package com.example.myaccount;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private final int activeColor = Color.parseColor("#ffffff");
    private final int normalColor = Color.parseColor("#ffffff");

    private final int activeSize = 17;
    private final int normalSize = 17;

    private ArrayList<Fragment> fragments;
    private TabLayoutMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ImageView back_button = findViewById(R.id.back_to_main);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this,MainActivity.class);
            startActivity(intent);
        });

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        final String[] tabs = new String[]{"支出", "收入"};

        //禁用预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //Adapter
        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                return TestFragment.newInstance(tabs[position]);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });
        //viewPager 页面切换监听监听
        viewPager2.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            //这里可以自定义TabView
            TextView tabView = new TextView(AddActivity.this);

            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{};

            int[] colors = new int[]{activeColor, normalColor};
            ColorStateList colorStateList = new ColorStateList(states, colors);
            tabView.setText(tabs[position]);
            tabView.setTextSize(normalSize);
            tabView.setTextColor(colorStateList);

            tab.setCustomView(tabView);
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();
    }

    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                assert tab != null;
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    assert tabView != null;
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    assert tabView != null;
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}