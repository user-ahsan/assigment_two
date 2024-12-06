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
    TextView usernameText;
    TextView emailText; 
    TextView themeText;
    TextView notificationsText;
    
    SharedPreferences savedData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        View screen = inflater.inflate(R.layout.fragment_profile_view, container, false);
        
        savedData = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        
        usernameText = screen.findViewById(R.id.username_text);
        emailText = screen.findViewById(R.id.email_text);
        themeText = screen.findViewById(R.id.theme_text);
        notificationsText = screen.findViewById(R.id.notifications_text);

        showUserInfo();
        
        return screen;
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserInfo();
    }

    private void showUserInfo() {
        String name = savedData.getString("username", getString(R.string.not_set));
        String email = savedData.getString("email", getString(R.string.not_set));
        String theme = savedData.getString("theme_name", getString(R.string.not_set));
        boolean notifications = savedData.getBoolean("notifications", false);

        usernameText.setText(getString(R.string.username_format, name));
        emailText.setText(getString(R.string.email_format, email));
        themeText.setText(getString(R.string.theme_format, theme));
        
        String notifStatus;
        if(notifications) {
            notifStatus = getString(R.string.enabled);
        } else {
            notifStatus = getString(R.string.disabled);
        }
        notificationsText.setText(getString(R.string.notifications_format, notifStatus));
    }
}