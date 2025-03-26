package com.example.projekt_rugbytrainings;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        TextView tvStats = findViewById(R.id.tv_stats);
        EditText etWeight = findViewById(R.id.et_weight);
        EditText etDistance = findViewById(R.id.et_distance);
        EditText etCalories = findViewById(R.id.et_calories);
        Button btnSaveStats = findViewById(R.id.btn_save_stats);

        btnSaveStats.setOnClickListener(v -> saveStats(dbHelper, etWeight, etDistance, etCalories, tvStats));

        loadStats(dbHelper, tvStats);
    }

    private void saveStats(DatabaseHelper dbHelper, EditText etWeight, EditText etDistance, EditText etCalories, TextView tvStats) {
        String weightStr = etWeight.getText().toString().trim();
        String distanceStr = etDistance.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(distanceStr) || TextUtils.isEmpty(caloriesStr)) {
            Toast.makeText(StatisticsActivity.this, "Vyplňte všechny údaje", Toast.LENGTH_SHORT).show();
            return;
        }

        int weight = Integer.parseInt(weightStr);
        float distance = Float.parseFloat(distanceStr);
        int calories = Integer.parseInt(caloriesStr);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("weight", weight);
        values.put("distance", distance);
        values.put("calories", calories);

        long id = db.insert("stats", null, values);
        if (id != -1) {
            Toast.makeText(StatisticsActivity.this, "Statistiky uloženy", Toast.LENGTH_SHORT).show();
            loadStats(dbHelper, tvStats);
        } else {
            Toast.makeText(StatisticsActivity.this, "Chyba při ukládání", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStats(DatabaseHelper dbHelper, TextView tvStats) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("stats", null, null, null, null, null, "id DESC");

        StringBuilder statsText = new StringBuilder("📊 Poslední statistiky:\n");

        while (cursor.moveToNext()) {
            int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
            float distance = cursor.getFloat(cursor.getColumnIndexOrThrow("distance"));
            int calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"));

            statsText.append("Váha: ").append(weight).append(" kg\n")
                    .append("Uběhnuto: ").append(distance).append(" km\n")
                    .append("Kalorie: ").append(calories).append(" kcal\n")
                    .append("---------------------\n");
        }
        cursor.close();
        tvStats.setText(statsText.toString());
    }
}




