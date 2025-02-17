package com.example.tsasoftwaredevassignment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tableSpecies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable edge-to-edge layout for this activity
        EdgeToEdge.enable(this);

        // set the content view to the layout for this activity
        setContentView(R.layout.activity_table_species);

        // set a listener to adjust the padding of the main view based on system bar insets (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // get the insets for the system bars (status bar, navigation bar)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // apply the insets to the view to ensure proper layout with system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets; // return the insets for further use if necessary
        });
    }
}