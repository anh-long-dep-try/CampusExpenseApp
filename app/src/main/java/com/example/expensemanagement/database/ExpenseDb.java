package com.example.expensemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ExpenseDb {
    private SQLiteDatabase dbWrite;

    public ExpenseDb(Context context) {
        DatabaseContext dbContext = new DatabaseContext(context);
        this.dbWrite = dbContext.getWritableDatabase();
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

    // Add methods for updating, deleting, and querying expenses as needed.
}
