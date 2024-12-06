package com.ahsan.assigment2;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call parent class constructor first
        super.onCreate(savedInstanceState);
        
        // Set the layout file to show
        setContentView(R.layout.activity_main);
        
        // Find the main layout view
        View mainView = findViewById(R.id.main);
        
        // Add some padding around the edges
        int padding = 16; // pixels of padding
        mainView.setPadding(padding, padding, padding, padding);
    }
}