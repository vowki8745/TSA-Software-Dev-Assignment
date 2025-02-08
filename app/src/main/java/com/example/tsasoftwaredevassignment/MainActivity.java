package com.example.tsasoftwaredevassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Check for deprecation and alternatives
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("MainActivity", "View with ID 'main' not found in the layout.");
        }
    }
    public void launchDanger(View v){
        //launchDangerNearME
        Intent i = new Intent(this, DangerNearMe.class);
        startActivity(i);
    }
    public void launchFuture(View v) {
        //launch FuturePrevalence
        Intent i = new Intent(this, FuturePrevalence.class);
        startActivity(i);
    }}