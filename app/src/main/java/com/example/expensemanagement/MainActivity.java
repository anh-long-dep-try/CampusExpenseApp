package com.example.expensemanagement;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.expensemanagement.adapters.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        MainPagerAdapter adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        findViewById(R.id.btnHome).setOnClickListener(v -> viewPager.setCurrentItem(0, true));
        findViewById(R.id.btnCategories).setOnClickListener(v -> viewPager.setCurrentItem(1, true));
        findViewById(R.id.btnTransactions).setOnClickListener(v -> viewPager.setCurrentItem(2, true));
        findViewById(R.id.btnSettings).setOnClickListener(v -> viewPager.setCurrentItem(3, true));
    }
}