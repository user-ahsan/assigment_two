package com.ahsan.assigment2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class UserSettings extends Fragment {
    EditText username, email, password;
    Spinner theme;
    CheckBox notifications;
    Button save, reset;
    SharedPreferences data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_settings, container, false);
        
        username = view.findViewById(R.id.username_input);
        email = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);
        theme = view.findViewById(R.id.theme_spinner);
        notifications = view.findViewById(R.id.notifications_checkbox);
        save = view.findViewById(R.id.save_settings_button);
        reset = view.findViewById(R.id.reset_button);
        
        data = getActivity().getSharedPreferences("UserPrefs", 0);
        
        ArrayAdapter<CharSequence> themes = ArrayAdapter.createFromResource(getActivity(),
                R.array.theme_options, android.R.layout.simple_spinner_item);
        theme.setAdapter(themes);
        
        loadSettings();
        
        TextWatcher watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void afterTextChanged(Editable s) {
                checkFields();
            }
        };
        
        username.addTextChangedListener(watcher);
        email.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        
        save.setOnClickListener(v -> saveSettings());
        reset.setOnClickListener(v -> resetSettings());
        
        return view;
    }
    
    void loadSettings() {
        username.setText(data.getString("username", ""));
        email.setText(data.getString("email", ""));
        password.setText(data.getString("password", ""));
        theme.setSelection(data.getInt("theme", 0));
        notifications.setChecked(data.getBoolean("notifications", false));
    }
    
    void checkFields() {
        boolean valid = !username.getText().toString().isEmpty() && 
                       !email.getText().toString().isEmpty() && 
                       !password.getText().toString().isEmpty();
        save.setEnabled(valid);
        save.setAlpha(valid ? 1.0f : 0.5f);
    }
    
    void saveSettings() {
        data.edit()
            .putString("username", username.getText().toString())
            .putString("email", email.getText().toString())
            .putString("password", password.getText().toString())
            .putInt("theme", theme.getSelectedItemPosition())
            .putBoolean("notifications", notifications.isChecked())
            .apply();
        Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
    }
    
    void resetSettings() {
        username.setText("");
        email.setText("");
        password.setText("");
        theme.setSelection(0);
        notifications.setChecked(false);
        data.edit().clear().apply();
        Toast.makeText(getActivity(), "Reset!", Toast.LENGTH_SHORT).show();
    }
}