package com.ahsan.assigment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class UserSettings extends Fragment {
    EditText usernameInput;
    EditText emailInput; 
    EditText passwordInput;
    Spinner themeSpinner;
    CheckBox notificationsCheckbox;
    Button saveButton;
    Button resetButton;
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
        resetButton = screen.findViewById(R.id.reset_button);

        ArrayAdapter<CharSequence> spinnerChoices = ArrayAdapter.createFromResource(getActivity(),
                R.array.theme_options, android.R.layout.simple_spinner_item);
        spinnerChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(spinnerChoices);

        loadOldSettings();

        usernameInput.addTextChangedListener(new ValidationTextWatcher(usernameInput));
        emailInput.addTextChangedListener(new ValidationTextWatcher(emailInput));
        passwordInput.addTextChangedListener(new ValidationTextWatcher(passwordInput));

        resetButton.setOnClickListener(v -> resetSettings());
        saveButton.setOnClickListener(v -> saveNewSettings());

        return screen;
    }

    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable s) {
            int viewId = view.getId();
            if (viewId == R.id.username_input) {
                validateUsername();
            } else if (viewId == R.id.email_input) {
                validateEmail();
            } else if (viewId == R.id.password_input) {
                validatePassword();
            }
            updateSaveButtonState();
        }
    }

    private void loadOldSettings() {
        usernameInput.setText(savedData.getString("username", ""));
        emailInput.setText(savedData.getString("email", ""));
        passwordInput.setText(savedData.getString("password", ""));
        themeSpinner.setSelection(savedData.getInt("theme_position", 0));
        notificationsCheckbox.setChecked(savedData.getBoolean("notifications", false));
    }

    private void validateUsername() {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInput.setError(getString(R.string.username_required));
        } else if (username.length() < 3) {
            usernameInput.setError(getString(R.string.username_length));
        } else {
            usernameInput.setError(null);
        }
    }

    private void validateEmail() {
        String email = emailInput.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        
        if (email.isEmpty()) {
            emailInput.setError(getString(R.string.email_required));
        } else if (!email.matches(emailPattern)) {
            emailInput.setError(getString(R.string.email_invalid));
        } else {
            emailInput.setError(null);
        }
    }

    private void validatePassword() {
        String password = passwordInput.getText().toString();
        if (password.isEmpty()) {
            passwordInput.setError(getString(R.string.password_required));
        } else if (password.length() < 6) {
            passwordInput.setError(getString(R.string.password_length));
        } else if (!password.matches(".*[A-Z].*")) {
            passwordInput.setError(getString(R.string.password_uppercase));
        } else if (!password.matches(".*[0-9].*")) {
            passwordInput.setError(getString(R.string.password_number));
        } else {
            passwordInput.setError(null);
        }
    }

    private void updateSaveButtonState() {
        boolean isValid = usernameInput.getError() == null && emailInput.getError() == null && 
                         passwordInput.getError() == null && !usernameInput.getText().toString().isEmpty() && 
                         !emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty();
        
        saveButton.setEnabled(isValid);
        saveButton.setAlpha(isValid ? 1.0f : 0.5f);
    }

    private void resetSettings() {
        new AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.reset_title))
            .setMessage(getString(R.string.reset_message))
            .setPositiveButton(getString(R.string.reset_confirm), (dialog, which) -> {
                usernameInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
                themeSpinner.setSelection(0);
                notificationsCheckbox.setChecked(false);
                savedData.edit().clear().apply();
                Toast.makeText(getActivity(), getString(R.string.reset_success), Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton(getString(R.string.reset_cancel), null)
            .show();
    }

    private void saveNewSettings() {
        validateUsername();
        validateEmail();
        validatePassword();

        if (usernameInput.getError() != null || emailInput.getError() != null || 
            passwordInput.getError() != null) {
            Toast.makeText(getActivity(), getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = savedData.edit();
        editor.putString("username", usernameInput.getText().toString().trim());
        editor.putString("email", emailInput.getText().toString().trim());
        editor.putString("password", passwordInput.getText().toString());
        editor.putInt("theme_position", themeSpinner.getSelectedItemPosition());
        editor.putString("theme_name", themeSpinner.getSelectedItem().toString());
        editor.putBoolean("notifications", notificationsCheckbox.isChecked());
        editor.apply();

        Toast.makeText(getActivity(), getString(R.string.save_success), Toast.LENGTH_SHORT).show();
    }
}