package com.example.tsasoftwaredevassignment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PrevalenceTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevalence_table);

        // get the county passed through the intent
        String county = getIntent().getStringExtra("county");

        // set the county name in the TextView
        TextView countyOut = findViewById(R.id.countyOut);
        countyOut.setText("County: " + county);

        // get the TableLayout to populate it
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // if the county is not null, populate the table
        if (county != null) {
            populateTable(tableLayout, county);
        } else {
            Log.e("PrevalenceTable", "County is null");
        }
    }

    // method to populate the table with species and their counts
    private void populateTable(TableLayout tableLayout, String county) {
        Map<String, Integer> speciesCount = new HashMap<>();

        try {
            // open the CSV file from the assets folder
            InputStream inputStream = getAssets().open("final_predictions.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // skip the header row
                    firstLine = false;
                    continue;
                }

                // split the CSV line by commas and extract the relevant columns
                String[] columns = line.split(",");
                if (columns.length < 6) continue; // skip rows with insufficient data

                String rowCounty = columns[5].trim(); // county column
                String species = columns[7].trim();  // common name column
                int count = Integer.parseInt(columns[4].trim()); // predicted count column

                // if the county matches, add to the species count
                if (rowCounty.equalsIgnoreCase(county)) {
                    speciesCount.put(species, speciesCount.getOrDefault(species, 0) + count);
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e("PrevalenceTable", "Error reading CSV file", e);
        }

        // sort the species count in descending order based on count
        TreeMap<String, Integer> sortedSpecies = new TreeMap<>((a, b) -> speciesCount.get(b).compareTo(speciesCount.get(a)));
        sortedSpecies.putAll(speciesCount);

        // iterate through the sorted species and add rows to the table
        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedSpecies.entrySet()) {
            TableRow row = new TableRow(this);
            TextView rankView = new TextView(this);
            TextView speciesView = new TextView(this);

            // set the rank and species name in the respective TextViews
            rankView.setText(String.valueOf(rank));
            speciesView.setText(entry.getKey());

            // add padding to the TextViews for better appearance
            rankView.setPadding(8, 8, 8, 8);
            speciesView.setPadding(8, 8, 8, 8);

            // add the TextViews to the row and the row to the table
            row.addView(rankView);
            row.addView(speciesView);
            tableLayout.addView(row);

            rank++;
        }

        // display the future most prevalent species if available
        if (!sortedSpecies.isEmpty()) {
            String futureSpecies = sortedSpecies.firstKey();
            TextView futureSpeciesOut = findViewById(R.id.futureSpeciesOut);
            futureSpeciesOut.setText("Future Most Prevalent Species: " + futureSpecies);
        }
    }
}