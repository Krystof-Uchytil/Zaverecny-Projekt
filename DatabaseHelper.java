package com.example.projekt_rugbytrainings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RugbyTrainer.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_TRAINING_FREQUENCY = "training_frequency";
    private static final String COLUMN_LEAGUE_LEVEL = "league_level";

    private static final String TABLE_STATISTICS = "statistics";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_WEIGHT_GAINED = "weight_gained";
    private static final String COLUMN_TRAINING_SESSIONS = "training_sessions";

    private static final String TABLE_TRAINING_PLANS = "training_plans";
    private static final String COLUMN_PLAN_DETAILS = "plan_details";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_HEIGHT + " REAL, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_POSITION + " TEXT, " +
                COLUMN_TRAINING_FREQUENCY + " INTEGER, " +
                COLUMN_LEAGUE_LEVEL + " TEXT)";

        String createStatisticsTable = "CREATE TABLE " + TABLE_STATISTICS + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_DISTANCE + " REAL, " +
                COLUMN_WEIGHT_GAINED + " REAL, " +
                COLUMN_TRAINING_SESSIONS + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_EMAIL + "))";

        String createTrainingPlansTable = "CREATE TABLE " + TABLE_TRAINING_PLANS + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PLAN_DETAILS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_EMAIL + "))";

        db.execSQL(createUsersTable);
        db.execSQL(createStatisticsTable);
        db.execSQL(createTrainingPlansTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_PLANS);
        onCreate(db);
    }

    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateUserProfile(String email, double height, double weight, String position, int frequency, String leagueLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_POSITION, position);
        values.put(COLUMN_TRAINING_FREQUENCY, frequency);
        values.put(COLUMN_LEAGUE_LEVEL, leagueLevel);
        int rowsUpdated = db.update(TABLE_USERS, values, COLUMN_EMAIL + "=?", new String[]{email});
        return rowsUpdated > 0;
    }

    // 📌 **Nová metoda pro načtení profilu uživatele**
    public Cursor getUserProfile(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
    }

    public boolean saveTrainingPlan(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        long result = db.insertWithOnConflict(TABLE_TRAINING_PLANS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public String getTrainingPlan(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PLAN_DETAILS + " FROM " + TABLE_TRAINING_PLANS + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
        if (cursor.moveToFirst()) {
            String plan = cursor.getString(0);
            cursor.close();
            return plan;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean updateStatistics(String email, double distance, double weightGained, int trainingSessions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTANCE, distance);
        values.put(COLUMN_WEIGHT_GAINED, weightGained);
        values.put(COLUMN_TRAINING_SESSIONS, trainingSessions);
        int rowsUpdated = db.update(TABLE_STATISTICS, values, COLUMN_EMAIL + "=?", new String[]{email});
        if (rowsUpdated == 0) {
            values.put(COLUMN_EMAIL, email);
            long insertResult = db.insert(TABLE_STATISTICS, null, values);
            return insertResult != -1;
        }
        return true;
    }

    public Cursor getStatistics(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STATISTICS + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
    }
}













