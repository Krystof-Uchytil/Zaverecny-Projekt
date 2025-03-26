package com.example.projekt_rugbytrainings;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class TrainingPlanActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan);

        TextView tvTrainingPlan = findViewById(R.id.tv_training_plan);
        TextView tvLocation = findViewById(R.id.tv_location);
        Button btnUpdateLocation = findViewById(R.id.btn_update_location);

        tvTrainingPlan.setText(getString(R.string.training_plan));

        btnUpdateLocation.setOnClickListener(v -> checkLocationPermission(tvLocation));
    }

    private void checkLocationPermission(TextView tvLocation) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation(tvLocation);
        }
    }

    private void getLocation(TextView tvLocation) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            tvLocation.setText(getString(R.string.location_text, location.getLatitude(), location.getLongitude()));
                        } else {
                            tvLocation.setText(R.string.location_unavailable);
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TextView tvLocation = findViewById(R.id.tv_location);
                getLocation(tvLocation);
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}




