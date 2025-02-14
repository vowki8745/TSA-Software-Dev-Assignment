package com.example.tsasoftwaredevassignment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Dates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);

        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        String countyFilter = getIntent().getStringExtra("county"); // e.g., "Brevard" or "false"

        startDate = formatDate(startDate);
        endDate = formatDate(endDate);

        boolean filterByCounty = countyFilter != null
                && !countyFilter.trim().isEmpty()
                && !countyFilter.equalsIgnoreCase("false");

        TextView titleText = findViewById(R.id.titleText);
        if (filterByCounty) {
            titleText.setText("Most prevalent species in " + countyFilter);
        } else {
            titleText.setText("Most prevalent species");
        }

        Map<String, Integer> speciesCount = new HashMap<>();

        try {
            InputStream is = getAssets().open("srcdata.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;


            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 5) continue;  // Skip if not enough columns.

                String comName = tokens[1].trim();
                String county = tokens[2].trim();
                String month = tokens[3].trim();
                int count;
                try {
                    count = Integer.parseInt(tokens[4].trim());
                } catch (NumberFormatException e) {
                    continue; // Skip rows with invalid count.
                }

                if (filterByCounty && !county.equalsIgnoreCase(countyFilter)) {
                    continue;
                }

                if (startDate != null && endDate != null) {
                    if (month.compareTo(startDate) < 0 || month.compareTo(endDate) > 0) {
                        continue;
                    }
                }

                speciesCount.put(comName, speciesCount.getOrDefault(comName, 0) + count);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> sortedSpecies = new ArrayList<>(speciesCount.entrySet());
        Collections.sort(sortedSpecies, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

         TableLayout tableLayout = findViewById(R.id.tableLayout);

        TableRow headerRow = new TableRow(this);
        TextView rankHeader = new TextView(this);
        rankHeader.setText("Rank");
        rankHeader.setTypeface(null, Typeface.BOLD);
        rankHeader.setPadding(8, 8, 8, 8);

        TextView speciesHeader = new TextView(this);
        speciesHeader.setText("Common Name");
        speciesHeader.setTypeface(null, Typeface.BOLD);
        speciesHeader.setPadding(8, 8, 8, 8);

        TextView countHeader = new TextView(this);
        countHeader.setText("Count");
        countHeader.setTypeface(null, Typeface.BOLD);
        countHeader.setPadding(8, 8, 8, 8);

        headerRow.addView(rankHeader);
        headerRow.addView(speciesHeader);
        headerRow.addView(countHeader);
        tableLayout.addView(headerRow);

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedSpecies) {
            TableRow row = new TableRow(this);

            TextView rankText = new TextView(this);
            rankText.setText(String.valueOf(rank));
            rankText.setPadding(8, 8, 8, 8);

            TextView speciesText = new TextView(this);
            speciesText.setText(entry.getKey());
            speciesText.setPadding(8, 8, 8, 8);

            TextView countText = new TextView(this);
            countText.setText(String.valueOf(entry.getValue()));
            countText.setPadding(8, 8, 8, 8);

            row.addView(rankText);
            row.addView(speciesText);
            row.addView(countText);
            tableLayout.addView(row);

            rank++;
        }
    }


    private String formatDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        if (dateStr.matches("\\d{4}-\\d{2}")) {
            return dateStr;
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();

            return dateStr;
        }
    }
}


