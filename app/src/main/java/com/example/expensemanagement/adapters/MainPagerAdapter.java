package com.example.expensemanagement.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.expensemanagement.fragments.HomeFragment;
import com.example.expensemanagement.fragments.ExpenseFragment;
import com.example.expensemanagement.fragments.BudgetFragment;
import com.example.expensemanagement.fragments.ProfileFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ExpenseFragment();
            case 2:
                return new BudgetFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Total number of tabs
    }
}
