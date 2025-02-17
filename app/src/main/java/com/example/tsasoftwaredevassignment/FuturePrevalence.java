// package declaration
package com.example.tsasoftwaredevassignment;

// importing necessary resources
import static com.example.tsasoftwaredevassignment.R.id.spinner2;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// class declaration with spinner interface
public class FuturePrevalence extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // spinner variable declaration
    Spinner spinner2;

    // onCreate method called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_prevalence);

        // check and set window insets if main view is present
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

        // initialize spinner and set adapter with items from resources
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setPrompt("select county");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);

        // set dropdown style and listener for spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
    }

    // method called when item is selected from spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "you selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
    }

    // method called when nothing is selected from spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // no action needed
    }

    // method to launch table activity with selected county
    public void launchTable(View v) {
        Intent i = new Intent(this, PrevalenceTable.class);
        String county = (String) spinner2.getSelectedItem();
        i.putExtra("county", county);
        startActivity(i);
    }
}
