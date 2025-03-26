package com.example.projekt_rugbytrainings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> registerUser(etEmail, etPassword));
    }

    private void registerUser(EditText etEmail, EditText etPassword) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Vyplňte email a heslo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.registerUser(email, password)) {
            Toast.makeText(RegisterActivity.this, "Registrace úspěšná", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Registrace selhala", Toast.LENGTH_SHORT).show();
        }
    }
}


