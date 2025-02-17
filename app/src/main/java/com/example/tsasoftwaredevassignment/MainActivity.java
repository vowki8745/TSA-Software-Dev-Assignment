// package declaration
package com.example.tsasoftwaredevassignment;

// importing necessary resources
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// main activity class
public class MainActivity extends AppCompatActivity {

    // onCreate method called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check and set window insets if main view is present
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("MainActivity", "view with id 'main' not found in the layout.");
        }
    }

    // method to launch danger near me activity
    public void launchDanger(View v) {
        Intent i = new Intent(this, DangerNearMe.class);
        startActivity(i);
    }

    // method to launch future prevalence activity
    public void launchFuture(View v) {
        Intent i = new Intent(this, FuturePrevalence.class);
        startActivity(i);
    }
}
