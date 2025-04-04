package com.example.expensemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.expensemanagement.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDb {
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public ExpenseDb(Context context) {
        DatabaseContext dbContext = new DatabaseContext(context);
        this.dbWrite = dbContext.getWritableDatabase();
        this.dbRead = dbContext.getReadableDatabase();
    }

    public long insertExpense(double amount, String category, String date, String description, int userId) {
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("category", category);
        values.put("date", date);
        values.put("description", description);
        values.put("user_id", userId);
        return dbWrite.insert("expenses", null, values);
    }

    public double getTotalIncomes() {
        Cursor cursor = dbRead.rawQuery("SELECT SUM(amount) FROM expenses WHERE amount > 0", null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        cursor.close();
        return 0;
    }

    public double getTotalExpenses() {
        Cursor cursor = dbRead.rawQuery("SELECT SUM(amount) FROM expenses WHERE amount < 0", null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        cursor.close();
        return 0;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM expenses", null);
        while (cursor.moveToNext()) {
            Expense expense = new Expense();
            expense.setId(cursor.getInt(cursor.getColumnIndex("id")));
            expense.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
            expense.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            expense.setDate(cursor.getString(cursor.getColumnIndex("date")));
            expense.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            expenses.add(expense);
        }
        cursor.close();
        return expenses;
    }
}
