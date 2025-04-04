package com.example.expensemanagement.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.ExpenseAdapter;
import com.example.expensemanagement.database.ExpenseDb;
import com.example.expensemanagement.model.Expense;

import java.util.List;

public class ExpenseFragment extends Fragment {
    private RecyclerView rvExpenses;
    private ExpenseAdapter adapter;
    private ExpenseDb expenseDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        rvExpenses = view.findViewById(R.id.rvExpenses);
        rvExpenses.setLayoutManager(new LinearLayoutManager(getContext()));

        expenseDb = new ExpenseDb(getContext());
        loadExpenses();

        return view;
    }

    private void loadExpenses() {
        List<Expense> expenses = expenseDb.getAllExpenses();
        adapter = new ExpenseAdapter(expenses, expense -> {
            // Handle edit or delete actions
            Toast.makeText(getContext(), "Clicked: " + expense.getDescription(), Toast.LENGTH_SHORT).show();
        });
        rvExpenses.setAdapter(adapter);
    }
}
