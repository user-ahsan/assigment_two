package com.ahsan.assigment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserSettings extends Fragment {
    // Declare our UI elements
    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Spinner themeSpinner;
    private CheckBox notificationsCheckbox;
    private Button saveButton;
    
    // For saving data
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_user_settings, container, false);
        
        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        
        // Find all our views
        usernameInput = view.findViewById(R.id.username_input);
        emailInput = view.findViewById(R.id.email_input);
        passwordInput = view.findViewById(R.id.password_input);
        themeSpinner = view.findViewById(R.id.theme_spinner);
        notificationsCheckbox = view.findViewById(R.id.notifications_checkbox);
        saveButton = view.findViewById(R.id.save_settings_button);

        // Set up the theme spinner with themes from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.theme_options,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);

        // Load any saved settings
        loadSavedSettings();

        // Set up save button click listener
        saveButton.setOnClickListener(v -> saveSettings());

        return view;
    }

    private void loadSavedSettings() {
        // Get saved values with default values if none exist
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        int themePosition = sharedPreferences.getInt("theme_position", 0);
        boolean notifications = sharedPreferences.getBoolean("notifications", false);

        // Set the values to our UI elements
        usernameInput.setText(username);
        emailInput.setText(email);
        passwordInput.setText(password);
        themeSpinner.setSelection(themePosition);
        notificationsCheckbox.setChecked(notifications);
    }

    private void saveSettings() {
        // Get the values from our UI elements
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        int themePosition = themeSpinner.getSelectedItemPosition();
        boolean notifications = notificationsCheckbox.isChecked();

        // Simple validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.fill_fields_error), Toast.LENGTH_SHORT).show();
            return;
        }

        // Create editor as a local variable instead of a field
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putInt("theme_position", themePosition);
        editor.putString("theme_name", themeSpinner.getSelectedItem().toString());
        editor.putBoolean("notifications", notifications);
        editor.apply();

        // Show success message
        Toast.makeText(requireContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();
    }
}