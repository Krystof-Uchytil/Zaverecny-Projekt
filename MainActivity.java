package com.example.projekt_rugbytrainings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnProfile, btnTrainingPlan, btnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnProfile = findViewById(R.id.btnProfile);
        btnTrainingPlan = findViewById(R.id.btnTrainingPlan);
        btnStatistics = findViewById(R.id.btnStatistics);

        btnLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        btnTrainingPlan.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TrainingPlanActivity.class)));
        btnStatistics.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatisticsActivity.class)));
    }
}

// LoginActivity.java
package com.example.projekt_rugbytrainings;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}

// ProfileActivity.java
package com.example.projekt_rugbytrainings;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}

// TrainingPlanActivity.java
package com.example.projekt_rugbytrainings;

public class TrainingPlanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan);
    }
}

// StatisticsActivity.java
package com.example.projekt_rugbytrainings;

public class StatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
}
