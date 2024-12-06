package com.ahsan.assigment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileView extends Fragment {
    // Declare our TextViews
    private TextView usernameText;
    private TextView emailText;
    private TextView themeText;
    private TextView notificationsText;
    
    // For reading saved data
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        
        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        
        // Find all our TextViews
        usernameText = view.findViewById(R.id.username_text);
        emailText = view.findViewById(R.id.email_text);
        themeText = view.findViewById(R.id.theme_text);
        notificationsText = view.findViewById(R.id.notifications_text);

        // Load and display the settings
        updateProfile();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update the display whenever the fragment becomes visible
        updateProfile();
    }

    private void updateProfile() {
        // Get the saved values with default values if none exist
        String username = sharedPreferences.getString("username", getString(R.string.not_set));
        String email = sharedPreferences.getString("email", getString(R.string.not_set));
        String theme = sharedPreferences.getString("theme_name", getString(R.string.not_set));
        boolean notifications = sharedPreferences.getBoolean("notifications", false);

        // Update the TextViews using string formatting
        usernameText.setText(getString(R.string.username_format, username));
        emailText.setText(getString(R.string.email_format, email));
        themeText.setText(getString(R.string.theme_format, theme));
        notificationsText.setText(getString(R.string.notifications_format, 
            notifications ? getString(R.string.enabled) : getString(R.string.disabled)));
    }
}