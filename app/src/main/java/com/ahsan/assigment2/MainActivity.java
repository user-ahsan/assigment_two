package com.ahsan.assigment2;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    Button settingsButton;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = findViewById(R.id.settings_button);
        profileButton = findViewById(R.id.profile_button);

        settingsButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UserSettings())
                .commit();
            settingsButton.setEnabled(false);
            profileButton.setEnabled(true);
        });

        profileButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProfileView())
                .commit();
            profileButton.setEnabled(false);
            settingsButton.setEnabled(true);
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UserSettings())
                .commit();
            settingsButton.setEnabled(false);
        }
    }
}