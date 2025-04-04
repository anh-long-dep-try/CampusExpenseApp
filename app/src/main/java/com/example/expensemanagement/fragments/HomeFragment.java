package com.example.expensemanagement.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.expensemanagement.R;
import com.example.expensemanagement.database.ExpenseDb;

public class HomeFragment extends Fragment {
    private TextView tvBalance, tvIncomes, tvExpenses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvBalance = view.findViewById(R.id.tvBalance);
        tvIncomes = view.findViewById(R.id.tvIncomes);
        tvExpenses = view.findViewById(R.id.tvExpenses);

        loadSummaryData();

        return view;
    }

    private void loadSummaryData() {
        ExpenseDb expenseDb = new ExpenseDb(getContext());
        double incomes = expenseDb.getTotalIncomes();
        double expenses = expenseDb.getTotalExpenses();
        double balance = incomes - expenses;

        tvIncomes.setText(String.format("Incomes: $%.2f", incomes));
        tvExpenses.setText(String.format("Expenses: $%.2f", expenses));
        tvBalance.setText(String.format("Balance: $%.2f", balance));
    }
}
