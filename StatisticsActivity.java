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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvStats;
    private EditText etUpdateWeight, etDistance, etCalories;
    private Button btnUpdateStats;
    private DatabaseHelper dbHelper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        tvStats = findViewById(R.id.tv_stats);
        etUpdateWeight = findViewById(R.id.et_update_weight);
        etDistance = findViewById(R.id.et_distance);
        etCalories = findViewById(R.id.et_calories);
        btnUpdateStats = findViewById(R.id.btn_update_stats);

        btnUpdateStats.setOnClickListener(v -> updateStats());

        loadStats();
    }

    private void updateStats() {
        String weightStr = etUpdateWeight.getText().toString().trim();
        String distanceStr = etDistance.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(distanceStr) || TextUtils.isEmpty(caloriesStr)) {
            Toast.makeText(StatisticsActivity.this, "Vyplňte všechny údaje", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(StatisticsActivity.this, "Uživatel není přihlášen", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("weight", Integer.parseInt(weightStr));
        values.put("distance", Float.parseFloat(distanceStr));
        values.put("calories", Integer.parseInt(caloriesStr));
        values.put("user_email", user.getEmail());

        long id = db.insert("stats", null, values);
        if (id != -1) {
            Toast.makeText(StatisticsActivity.this, "Statistiky aktualizovány", Toast.LENGTH_SHORT).show();
            loadStats();
        } else {
            Toast.makeText(StatisticsActivity.this, "Chyba při aktualizaci", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStats() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("stats", null, "user_email = ?", new String[]{user.getEmail()}, null, null, null);

        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            float distance = cursor.getFloat(cursor.getColumnIndex("distance"));
            int calories = cursor.getInt(cursor.getColumnIndex("calories"));
            sb.append("Váha: ").append(weight).append(" kg\n")
                    .append("Uběhnuté kilometry: ").append(distance).append(" km\n")
                    .append("Spálené kalorie: ").append(calories).append(" kcal\n")
                    .append("---------------------\n");
        }
        cursor.close();
        tvStats.setText(sb.toString());
    }
}

