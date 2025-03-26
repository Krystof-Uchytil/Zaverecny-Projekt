package com.example.projekt_rugbytrainings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Vyplňte email a heslo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUser(email, password)) { // ✅ Opravená volání checkUser()
            Toast.makeText(LoginActivity.this, "Přihlášení úspěšné", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Neplatný email nebo heslo", Toast.LENGTH_SHORT).show();
        }
    }
}





