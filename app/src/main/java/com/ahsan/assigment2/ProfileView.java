package com.ahsan.assigment2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileView extends Fragment {
    TextView username, email, theme, notifications;
    SharedPreferences data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        
        username = view.findViewById(R.id.username_text);
        email = view.findViewById(R.id.email_text);
        theme = view.findViewById(R.id.theme_text);
        notifications = view.findViewById(R.id.notifications_text);
        
        data = getActivity().getSharedPreferences("UserPrefs", 0);
        
        showInfo();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showInfo();
    }

    void showInfo() {
        String name = data.getString("username", "Not set");
        String mail = data.getString("email", "Not set");
        String themeChoice = data.getString("theme_name", "Default");
        boolean notifs = data.getBoolean("notifications", false);

        username.setText("Username: " + name);
        email.setText("Email: " + mail);
        theme.setText("Theme: " + themeChoice);
        notifications.setText("Notifications: " + (notifs ? "On" : "Off"));
    }
}