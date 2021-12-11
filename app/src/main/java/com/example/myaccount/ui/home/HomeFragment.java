package com.example.myaccount.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.databinding.FragmentHomeBinding;
import com.example.myaccount.ui.cards.BarCard;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private LinearLayout test;
    private BarCard card;
    private DataBase accountDataBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        test = binding.testss;
<<<<<<< HEAD
        accountDataBase = Room.inMemoryDatabaseBuilder(getContext(), AccountDataBase.class).build();
=======
        accountDataBase = Room.inMemoryDatabaseBuilder(getContext(), DataBase.class).allowMainThreadQueries().build();
>>>>>>> 85794db88146d1fd9c08522f947f4f236568a4e5
        homeViewModel.getContext().observe(getViewLifecycleOwner(), new Observer<Context>() {
            @Override
            public void onChanged(Context context) {
                card = new BarCard(context);
            }
        });
        homeViewModel.getIds(accountDataBase).observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                card.refresh();
            }
        });
        card = new BarCard(getContext());
        test.addView(card);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}