package com.ahsan.assigment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Button settingsButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = findViewById(R.id.settings_button);
        profileButton = findViewById(R.id.profile_button);

        settingsButton.setOnClickListener(v -> {
            loadFragment(new UserSettings());
            settingsButton.setEnabled(false);
            profileButton.setEnabled(true);
        });

        profileButton.setOnClickListener(v -> {
            loadFragment(new ProfileView());
            profileButton.setEnabled(false);
            settingsButton.setEnabled(true);
        });

        if (savedInstanceState == null) {
            loadFragment(new UserSettings());
            settingsButton.setEnabled(false);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        );

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}