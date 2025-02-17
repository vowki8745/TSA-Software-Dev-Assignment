package com.example.tsasoftwaredevassignment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

        // set the content view to activity_dates layout
        setContentView(R.layout.activity_dates);

        // getting start date from intent
        String startDate = getIntent().getStringExtra("startDate");

        // getting end date from intent
        String endDate = getIntent().getStringExtra("endDate");

        // getting county filter from intent
        String countyFilter = getIntent().getStringExtra("county");

        // formatting start date
        startDate = formatDate(startDate);

        // formatting end date
        endDate = formatDate(endDate);

        // checking if county filter is applied
        boolean filterByCounty = countyFilter != null
                && !countyFilter.trim().isEmpty()
                && !countyFilter.trim().equalsIgnoreCase("false");

        // log the countyFilter value to check if it's correctly passed
        Log.d("Intent Data", "Start Date: " + startDate);
        Log.d("Intent Data", "End Date: " + endDate);
        Log.d("Intent Data", "County Filter: " + countyFilter);

        // find the title textview by id
        TextView titleText = findViewById(R.id.titleText);

        // set the title text based on filter
        if (filterByCounty) {
            titleText.setText("Most prevalent species in " + countyFilter);
        } else {
            titleText.setText("Most prevalent species");
        }

        // create a hashmap to store species count
        Map<String, Integer> speciesCount = new HashMap<>();

        try {
            // open csv file from assets
            InputStream is = getAssets().open("srcdata.csv");

            // create buffered reader to read file
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            // reading file line by line
            while ((line = reader.readLine()) != null) {

                // split line by commas
                String[] tokens = line.split(",");

                // skip lines with insufficient columns
                if (tokens.length < 5) continue;

                // extract common name
                String comName = tokens[1].trim();

                // extract county name
                String county = tokens[2].trim();

                // extract month
                String month = tokens[3].trim();

                int count;
                try {
                    // parse count to integer
                    count = Integer.parseInt(tokens[4].trim());
                } catch (NumberFormatException e) {
                    // skip invalid count values
                    continue;
                }

                // log the extracted county to verify if it's being parsed correctly
                Log.d("CSV Parsing", "Parsed county: " + county);

                // check if county matches filter and apply it
                if (filterByCounty && !county.equalsIgnoreCase(countyFilter.trim())) {
                    continue;
                }

                // check if date range is valid
                if (startDate != null && endDate != null) {
                    // check if month is within range
                    if (month.compareTo(startDate) < 0 || month.compareTo(endDate) > 0) {
                        continue;
                    }
                }

                // add count to species map
                speciesCount.put(comName, speciesCount.getOrDefault(comName, 0) + count);
            }

            // close reader
            reader.close();
        } catch (IOException e) {
            // print error stack trace if file read fails
            e.printStackTrace();
        }

        // create list from hashmap entries
        List<Map.Entry<String, Integer>> sortedSpecies = new ArrayList<>(speciesCount.entrySet());

        // sorting species by count in descending order
        Collections.sort(sortedSpecies, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // find table layout by id
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // create a new table row for headers
        TableRow headerRow = new TableRow(this);

        // create textview for rank header
        TextView rankHeader = new TextView(this);

        // setting text for rank header
        rankHeader.setText("Rank");

        // setting bold text style
        rankHeader.setTypeface(null, Typeface.BOLD);

        // setting padding
        rankHeader.setPadding(8, 8, 8, 8);

        // create textview for species name header
        TextView speciesHeader = new TextView(this);

        // setting text for species name header
        speciesHeader.setText("Common Name");

        // setting bold text style
        speciesHeader.setTypeface(null, Typeface.BOLD);

        // setting padding
        speciesHeader.setPadding(8, 8, 8, 8);

        // adding headers to row
        headerRow.addView(rankHeader);
        headerRow.addView(speciesHeader);

        // adding header row to table layout
        tableLayout.addView(headerRow);

        // initializing rank counter
        int rank = 1;

        // looping through sorted species
        for (Map.Entry<String, Integer> entry : sortedSpecies) {

            // create new table row
            TableRow row = new TableRow(this);

            // create textview for rank column
            TextView rankText = new TextView(this);

            // setting rank text
            rankText.setText(String.valueOf(rank));

            // setting padding
            rankText.setPadding(8, 8, 8, 8);

            // create textview for species column
            TextView speciesText = new TextView(this);

            // setting species name
            speciesText.setText(entry.getKey());

            // setting padding
            speciesText.setPadding(8, 8, 8, 8);

            // adding textviews to row
            row.addView(rankText);
            row.addView(speciesText);

            // adding row to table layout
            tableLayout.addView(row);

            // incrementing rank
            rank++;
        }
    }

    private String formatDate(String dateStr) {
        // checking if date string is null
        if (dateStr == null) {
            return null;
        }

        // checking if already in yyyy-MM format
        if (dateStr.matches("\\d{4}-\\d{2}")) {
            return dateStr;
        }

        try {
            // defining input date format
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);

            // parsing date from string
            Date date = inputFormat.parse(dateStr);

            // defining output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);

            // returning formatted date
            return outputFormat.format(date);
        } catch (ParseException e) {
            // printing error stack trace if parsing fails
            e.printStackTrace();

            // returning input string if parsing fails
            return dateStr;
        }
    }
}


