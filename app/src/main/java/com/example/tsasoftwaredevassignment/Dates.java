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

        // Retrieve intent extras: startDate, endDate, and county.
        // Two buttons send dates as "YYYY-MM", the third sends them as "Jan 1 2000".
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        String countyFilter = getIntent().getStringExtra("county"); // e.g., "Brevard" or "false"

        // Convert the incoming dates into "yyyy-MM" format.
        startDate = formatDate(startDate);
        endDate = formatDate(endDate);

        // Determine if we are filtering by county.
        boolean filterByCounty = countyFilter != null
                && !countyFilter.trim().isEmpty()
                && !countyFilter.equalsIgnoreCase("false");

        // Update title based on the county filter.
        TextView titleText = findViewById(R.id.titleText);
        if (filterByCounty) {
            titleText.setText("Most prevalent species in " + countyFilter);
        } else {
            titleText.setText("Most prevalent species");
        }

        // Create a map to aggregate counts per common name.
        // Assuming the CSV columns are as follows:
        // Column 0: SciName, Column 1: ComName, Column 2: county, Column 3: month, Column 4: count.
        Map<String, Integer> speciesCount = new HashMap<>();

        try {
            // Ensure that "srcdata.csv" is placed in app/src/main/assets/
            InputStream is = getAssets().open("srcdata.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            // Optionally, skip header line if your CSV contains one.
            // String header = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 5) continue;  // Skip if not enough columns.

                // Use the common name from the second column (index 1).
                String comName = tokens[1].trim();
                String county = tokens[2].trim();
                String month = tokens[3].trim();
                int count;
                try {
                    count = Integer.parseInt(tokens[4].trim());
                } catch (NumberFormatException e) {
                    continue; // Skip rows with invalid count.
                }

                // If filtering by county, skip rows that do not match.
                if (filterByCounty && !county.equalsIgnoreCase(countyFilter)) {
                    continue;
                }

                // Filter by date range. Assuming the CSV "month" column is in "yyyy-MM" format.
                if (startDate != null && endDate != null) {
                    if (month.compareTo(startDate) < 0 || month.compareTo(endDate) > 0) {
                        continue;
                    }
                }

                // Aggregate counts per common name.
                speciesCount.put(comName, speciesCount.getOrDefault(comName, 0) + count);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the species by total count in descending order.
        List<Map.Entry<String, Integer>> sortedSpecies = new ArrayList<>(speciesCount.entrySet());
        Collections.sort(sortedSpecies, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Populate the table layout defined in activity_dates.xml.
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Create and add a header row.
        TableRow headerRow = new TableRow(this);
        TextView rankHeader = new TextView(this);
        rankHeader.setText("Rank");
        rankHeader.setTypeface(null, Typeface.BOLD);
        rankHeader.setPadding(8, 8, 8, 8);

        // Updated header text to "Common Name" to reflect usage of ComName.
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

        // Populate the table with species data.
        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedSpecies) {
            TableRow row = new TableRow(this);

            TextView rankText = new TextView(this);
            rankText.setText(String.valueOf(rank));
            rankText.setPadding(8, 8, 8, 8);

            TextView speciesText = new TextView(this);
            // Here, entry.getKey() represents the common name (ComName) from the dataset.
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

    /**
     * Converts an incoming date string to "yyyy-MM" format.
     * If the string is already in "yyyy-MM", it is returned unchanged.
     * Otherwise, it attempts to parse formats like "Jan 1 2000" (using Locale.ENGLISH).
     */
    private String formatDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        // Check if already in "yyyy-MM" format (e.g., "2020-07").
        if (dateStr.matches("\\d{4}-\\d{2}")) {
            return dateStr;
        }
        // Otherwise, try parsing a date like "Jan 1 2000".
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // If parsing fails, return the original string (or you could decide on a fallback).
            return dateStr;
        }
    }
}


