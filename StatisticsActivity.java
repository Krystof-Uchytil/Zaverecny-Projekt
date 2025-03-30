package com.example.projekt_rugbytrainings;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {
    private TextView tvDistance, tvWeightGained, tvTrainingSessions;
    private Button btnUpdateStats;
    private DatabaseHelper dbHelper;
    private String userEmail = "test@example.com"; // Přizpůsob si to podle přihlášeného uživatele

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvDistance = findViewById(R.id.tv_distance);
        tvWeightGained = findViewById(R.id.tv_weight_gain);
        tvTrainingSessions = findViewById(R.id.tv_trainings_count);
        btnUpdateStats = findViewById(R.id.btn_update_stats);

        dbHelper = new DatabaseHelper(this);
        loadStatistics();

        // Přidání listeneru na tlačítko pro aktualizaci statistik
        btnUpdateStats.setOnClickListener(v -> updateStats());
    }

    @SuppressLint("SetTextI18n")
    private void loadStatistics() {
        Cursor cursor = dbHelper.getStatistics(userEmail);
        if (cursor.moveToFirst()) {
            double distance = cursor.getDouble(cursor.getColumnIndexOrThrow("distance"));
            double weightGained = cursor.getDouble(cursor.getColumnIndexOrThrow("weight_gained"));
            int trainingSessions = cursor.getInt(cursor.getColumnIndexOrThrow("training_sessions"));

            tvDistance.setText("Uběhnutá vzdálenost: " + distance + " km");
            tvWeightGained.setText("Nabrana váha: " + weightGained + " kg");
            tvTrainingSessions.setText("Počet tréninků: " + trainingSessions);
        } else {
            Toast.makeText(this, "Žádné statistiky nejsou k dispozici.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void updateStats() {
        // Simulovaná nová data (můžeš to nahradit daty ze vstupů)
        double newDistance = 10.5;  // Například nový běh
        double newWeightGained = 1.2; // Přibraná váha
        int newSessions = 5; // Počet tréninků

        boolean success = dbHelper.updateStatistics(userEmail, newDistance, newWeightGained, newSessions);
        if (success) {
            Toast.makeText(this, "Statistiky aktualizovány!", Toast.LENGTH_SHORT).show();
            loadStatistics(); // Znovu načteme data
        } else {
            Toast.makeText(this, "Chyba při aktualizaci statistik.", Toast.LENGTH_SHORT).show();
        }
    }
}










