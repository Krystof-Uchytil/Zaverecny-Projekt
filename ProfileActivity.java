package com.example.projekt_rugbytrainings;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private EditText etClub, etHeight, etWeight, etPosition, etTrainingFrequency, etGoal;
    private Button btnSaveProfile;
    private DatabaseHelper dbHelper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        etClub = findViewById(R.id.et_club);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        etPosition = findViewById(R.id.et_position);
        etTrainingFrequency = findViewById(R.id.et_training_frequency);
        etGoal = findViewById(R.id.et_goal);
        btnSaveProfile = findViewById(R.id.btn_save_profile);

        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String club = etClub.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String trainingFrequency = etTrainingFrequency.getText().toString().trim();
        String goal = etGoal.getText().toString().trim();

        if (TextUtils.isEmpty(club) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)
                || TextUtils.isEmpty(position) || TextUtils.isEmpty(trainingFrequency) || TextUtils.isEmpty(goal)) {
            Toast.makeText(ProfileActivity.this, "Vyplňte všechny údaje", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(ProfileActivity.this, "Uživatel není přihlášen", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("club", club);
        values.put("height", Integer.parseInt(height));
        values.put("weight", Integer.parseInt(weight));
        values.put("position", position);
        values.put("trainingFrequency", trainingFrequency);
        values.put("goal", goal);

        long id = db.insert("users", null, values);
        if (id != -1) {
            Toast.makeText(ProfileActivity.this, "Profil uložen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfileActivity.this, "Chyba při ukládání", Toast.LENGTH_SHORT).show();
        }
    }
}

