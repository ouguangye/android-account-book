package com.example.myaccount.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.myaccount.MainActivity;
import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.dataBase.user.User;
import com.example.myaccount.databinding.FragmentHomeBinding;
import com.example.myaccount.ui.cards.BarCard;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private NestedScrollView test;
    private BarCard card;
    private DataBase accountDataBase;
    private User user;
    private boolean added = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        test = binding.testss;
        accountDataBase = Room.inMemoryDatabaseBuilder(getContext(), DataBase.class).allowMainThreadQueries().build();
        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        int[] dddd = homeViewModel.getIds(accountDataBase, user).getValue();
        homeViewModel.getIds(accountDataBase, user).observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                if (!added){
                    card = new BarCard(getContext(), user);
                    test.addView(card);
                }
                if (added)
                    card.refresh();
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}