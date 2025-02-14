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


        String county = getIntent().getStringExtra("county");


        TextView countyOut = findViewById(R.id.countyOut);
        countyOut.setText("County: " + county);


        TableLayout tableLayout = findViewById(R.id.tableLayout);


        if (county != null) {
            populateTable(tableLayout, county);
        } else {
            Log.e("PrevalenceTable", "County is null");
        }
    }

    private void populateTable(TableLayout tableLayout, String county) {
        Map<String, Integer> speciesCount = new HashMap<>();

        try {

            InputStream inputStream = getAssets().open("final_predictions.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // Skip header row
                    firstLine = false;
                    continue;
                }


                String[] columns = line.split(",");
                if (columns.length < 6) continue;

                String rowCounty = columns[5].trim(); // County column
                String species = columns[7].trim();  // Common Name
                int count = Integer.parseInt(columns[4].trim()); // Predicted Count


                if (rowCounty.equalsIgnoreCase(county)) {
                    speciesCount.put(species, speciesCount.getOrDefault(species, 0) + count);
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e("PrevalenceTable", "Error reading CSV file", e);
        }

        TreeMap<String, Integer> sortedSpecies = new TreeMap<>((a, b) -> speciesCount.get(b).compareTo(speciesCount.get(a)));
        sortedSpecies.putAll(speciesCount);


        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedSpecies.entrySet()) {
            TableRow row = new TableRow(this);
            TextView rankView = new TextView(this);
            TextView speciesView = new TextView(this);

            rankView.setText(String.valueOf(rank));
            speciesView.setText(entry.getKey());

            rankView.setPadding(8, 8, 8, 8);
            speciesView.setPadding(8, 8, 8, 8);

            row.addView(rankView);
            row.addView(speciesView);
            tableLayout.addView(row);

            rank++;
        }


        if (!sortedSpecies.isEmpty()) {
            String futureSpecies = sortedSpecies.firstKey();
            TextView futureSpeciesOut = findViewById(R.id.futureSpeciesOut);
            futureSpeciesOut.setText("Future Most Prevalent Species: " + futureSpecies);
        }
    }
}