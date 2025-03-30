package com.example.projekt_rugbytrainings;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private EditText etHeight, etWeight, etPosition, etFrequency, etLevel;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private String userEmail = "test@example.com"; // Předpoklad - musíš použít přihlášeného uživatele

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etHeight = findViewById(R.id.et_profile_height);
        etWeight = findViewById(R.id.et_profile_weight);
        etPosition = findViewById(R.id.et_profile_position);
        etFrequency = findViewById(R.id.et_profile_frequency);
        etLevel = findViewById(R.id.et_profile_level);
        btnSave = findViewById(R.id.btn_profile_save);

        dbHelper = new DatabaseHelper(this);

        loadUserProfile();

        btnSave.setOnClickListener(view -> saveUserProfile());
    }

    private void loadUserProfile() {
        Cursor cursor = dbHelper.getUserProfile(userEmail);
        if (cursor.moveToFirst()) {
            etHeight.setText(cursor.getString(cursor.getColumnIndexOrThrow("height")));
            etWeight.setText(cursor.getString(cursor.getColumnIndexOrThrow("weight")));
            etPosition.setText(cursor.getString(cursor.getColumnIndexOrThrow("position")));
            etFrequency.setText(cursor.getString(cursor.getColumnIndexOrThrow("frequency")));
            etLevel.setText(cursor.getString(cursor.getColumnIndexOrThrow("level")));
        }
        cursor.close();
    }

    private void saveUserProfile() {
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String frequencyStr = etFrequency.getText().toString().trim();
        String level = etLevel.getText().toString().trim();

        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(position) || TextUtils.isEmpty(frequencyStr) || TextUtils.isEmpty(level)) {
            Toast.makeText(this, "Vyplňte všechna pole", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        int frequency = Integer.parseInt(frequencyStr);

        boolean success = dbHelper.updateUserProfile(userEmail, height, weight, position, frequency, level);
        if (success) {
            Toast.makeText(this, "Profil byl aktualizován!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Chyba při aktualizaci profilu.", Toast.LENGTH_SHORT).show();
        }
    }
}






