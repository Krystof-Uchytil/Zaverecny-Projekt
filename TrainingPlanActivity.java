package com.example.projekt_rugbytrainings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class TrainingPlanActivity extends AppCompatActivity {

    private TextView tvNearestTrainingSpot, tvTrainingPlanResult;
    private Button btnGeneratePlan;
    private DatabaseHelper dbHelper;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan);

        // Inicializace UI
        tvNearestTrainingSpot = findViewById(R.id.tv_nearest_training_spot);
        tvTrainingPlanResult = findViewById(R.id.tv_training_plan_result);
        btnGeneratePlan = findViewById(R.id.btn_generate_plan);

        // Inicializace databáze a GPS klienta
        dbHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Nastavení akce pro tlačítko
        btnGeneratePlan.setOnClickListener(v -> generateTrainingPlan());
    }

    @SuppressLint("SetTextI18n")
    private void generateTrainingPlan() {
        // Získání polohy uživatele
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Najdeme nejbližší tréninkové místo
                String nearestSpot = findNearestTrainingSpot(location);
                tvNearestTrainingSpot.setText(getString(R.string.label_nearest_training_spot) + " " + nearestSpot);

                // Vygenerování tréninkového plánu
                String plan = generatePlan(nearestSpot);
                tvTrainingPlanResult.setText(plan);

                // Uložíme plán do databáze
                dbHelper.saveTrainingPlan(plan);
            } else {
                Toast.makeText(this, R.string.error_location, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Simulovaná metoda pro nalezení nejbližšího tréninkového místa
    private String findNearestTrainingSpot(Location location) {
        // Tohle by bylo reálně na základě databáze míst, zde jen ukázka
        return "Městský stadion";  // Návrat pevné hodnoty pro účely testu
    }

    // Generování tréninkového plánu na základě lokality
    private String generatePlan(String nearestSpot) {
        return getString(R.string.training_plan_generated) + " " + nearestSpot;
    }

    // Zpracování žádosti o povolení GPS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generateTrainingPlan();
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}







