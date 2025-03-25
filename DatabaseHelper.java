package com.example.projekt_rugbytrainings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rugbytrainings.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoření tabulky uživatelů
        String createUsers = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "club TEXT," +
                "height INTEGER," +
                "weight INTEGER," +
                "position TEXT," +
                "trainingFrequency TEXT," +
                "goal TEXT)";
        db.execSQL(createUsers);

        // Vytvoření tabulky statistik
        String createStats = "CREATE TABLE stats (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_email TEXT," +
                "weight INTEGER," +
                "distance REAL," +
                "calories INTEGER)";
        db.execSQL(createStats);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS stats");
        onCreate(db);
    }
}
