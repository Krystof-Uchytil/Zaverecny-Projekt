package com.example.projekt_rugbytrainings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rugby.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE, password TEXT)";
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // ✅ Opravená metoda `registerUser()`
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1; // Pokud je výsledek -1, registrace selhala
    }

    // ✅ Opravená metoda `checkUser()`
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}




