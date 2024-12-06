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

public class UserSettings extends Fragment {
    EditText usernameInput;
    EditText emailInput; 
    EditText passwordInput;
    Spinner themeSpinner;
    CheckBox notificationsCheckbox;
    Button saveButton;
    
    SharedPreferences savedData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View screen = inflater.inflate(R.layout.fragment_user_settings, container, false);
        
        savedData = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        
        usernameInput = screen.findViewById(R.id.username_input);
        emailInput = screen.findViewById(R.id.email_input);
        passwordInput = screen.findViewById(R.id.password_input);
        themeSpinner = screen.findViewById(R.id.theme_spinner);
        notificationsCheckbox = screen.findViewById(R.id.notifications_checkbox);
        saveButton = screen.findViewById(R.id.save_settings_button);

        ArrayAdapter<CharSequence> spinnerChoices = ArrayAdapter.createFromResource(getActivity(),
                R.array.theme_options,
                android.R.layout.simple_spinner_item);
        spinnerChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(spinnerChoices);

        loadOldSettings();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewSettings();
            }
        });

        return screen;
    }

    private void loadOldSettings() {
        String name = savedData.getString("username", "");
        String email = savedData.getString("email", "");
        String password = savedData.getString("password", "");
        int themeChoice = savedData.getInt("theme_position", 0);
        boolean notifications = savedData.getBoolean("notifications", false);

        usernameInput.setText(name);
        emailInput.setText(email);
        passwordInput.setText(password);
        themeSpinner.setSelection(themeChoice);
        notificationsCheckbox.setChecked(notifications);
    }

    private void saveNewSettings() {
        String name = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = savedData.edit();
        editor.putString("username", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putInt("theme_position", themeSpinner.getSelectedItemPosition());
        editor.putString("theme_name", themeSpinner.getSelectedItem().toString());
        editor.putBoolean("notifications", notificationsCheckbox.isChecked());
        editor.apply();

        Toast.makeText(getActivity(), "Settings saved!", Toast.LENGTH_SHORT).show();
    }
}