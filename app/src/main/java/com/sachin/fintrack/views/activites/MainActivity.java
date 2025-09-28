package com.sachin.fintrack.views.activites;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.sachin.fintrack.R;
import com.sachin.fintrack.databinding.ActivityMainBinding;
import com.sachin.fintrack.utils.Constants;
import com.sachin.fintrack.viewmodels.MainViewModel;
import com.sachin.fintrack.views.fragments.ChatbotFragment;
import com.sachin.fintrack.views.fragments.ProfileFragment;
import com.sachin.fintrack.views.fragments.StatsFragment;
import com.sachin.fintrack.views.fragments.TransactionsFragment;

import java.util.Calendar;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Calendar calendar;
    public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Constants.setCategories();
        calendar = Calendar.getInstance();
        loadFragment(new TransactionsFragment());
        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Fragment selectedFragment = null;

                switch (newIndex) {
                    case 0:
                        selectedFragment = new TransactionsFragment();
                        break;
                    case 1:
                        selectedFragment = new StatsFragment();
                        break;
                    case 2:
                        selectedFragment = new ChatbotFragment();
                        break;
                    case 3:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    public void getTransactions() {
        viewModel.getTransactions(calendar);
    }
}
