package com.example.excessbaggageapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // 1. Enable the back arrow in the top action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("About Developer");
        }

        // 2. Bind the custom "Back to Calculator" button from XML
        Button btnBack = findViewById(R.id.btnBack);

        // 3. Set click listener to close this screen and return to MainActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Handles the top action bar back arrow click
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}