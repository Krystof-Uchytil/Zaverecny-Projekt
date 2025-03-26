package com.example.projekt_rugbytrainings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnProfile = findViewById(R.id.btn_profile);
        Button btnTrainingPlan = findViewById(R.id.btn_training_plan);
        Button btnStatistics = findViewById(R.id.btn_statistics);
        Button btnLogout = findViewById(R.id.btn_logout);

        btnProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        btnTrainingPlan.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TrainingPlanActivity.class)));
        btnStatistics.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatisticsActivity.class)));
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}




