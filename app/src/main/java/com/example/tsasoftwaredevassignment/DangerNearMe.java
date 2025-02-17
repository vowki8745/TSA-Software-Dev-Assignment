package com.example.tsasoftwaredevassignment;

// import statements for android components
import static android.app.AlertDialog.THEME_HOLO_DARK;
import static android.content.DialogInterface.BUTTON_NEUTRAL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DangerNearMe extends AppCompatActivity {

    // declare date pickers and buttons
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton2;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danger_near_me);

        // apply window insets for immersive layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize date pickers and set default text
        initDatePicker();
        initDatePicker2();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getTodayDate2());

        // set up spinner for county selection
        spinner = findViewById(R.id.spinner);
        spinner.setPrompt("insert county");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // return today's date in formatted string
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    // configure first date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, 2, dateSetListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    // convert date components into string
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    // return month abbreviation from number
    private String getMonthFormat(int month) {
        if (month == 1) return "JAN";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "APR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AUG";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DEC";
        return "JAN";
    }

    // display first date picker dialog
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    // launch recent date range search activity
    public void launchRecent(View v) {
        Intent r = new Intent(this, Dates.class);
        r.putExtra("startDate", "2019-01");
        r.putExtra("endDate", "2024-12");
        r.putExtra("county", spinner.getSelectedItem().toString());
        startActivity(r);
    }

    // launch all-time date search activity
    public void launchAllTime(View v) {
        Intent a = new Intent(this, Dates.class);
        a.putExtra("county", spinner.getSelectedItem().toString());
        a.putExtra("startDate", "1900-01");
        a.putExtra("endDate", "2024-12");
        startActivity(a);
    }

    // launch custom date range search activity
    public void launchCustom(View v) {
        Intent a = new Intent(this, Dates.class);
        Button start = findViewById(R.id.datePickerButton);
        Button end = findViewById(R.id.datePickerButton2);
        a.putExtra("startDate", start.getText().toString());
        a.putExtra("endDate", end.getText().toString());
        a.putExtra("county", spinner.getSelectedItem().toString());
        startActivity(a);
    }

    // return today's date for second picker
    private String getTodayDate2() {
        Calendar cal = Calendar.getInstance();
        return makeDateString2(cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.YEAR));
    }

    // configure second date picker
    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener2 = (datePicker2, year, month, day) -> {
            month += 1;
            String date = makeDateString2(day, month, year);
            dateButton2.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        datePickerDialog2 = new DatePickerDialog(this, 2, dateSetListener2,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    // convert date components into string for second picker
    private String makeDateString2(int day, int month, int year) {
        return getMonthFormat2(month) + " " + day + " " + year;
    }

    // return month abbreviation for second picker
    private String getMonthFormat2(int month) {
        if(month == 1) return "JAN";
        if(month == 2) return "FEB";
        if(month == 3) return "MAR";
        if(month == 4) return "APR";
        if(month == 5) return "MAY";
        if(month == 6) return "JUN";
        if(month == 7) return "JUL";
        if(month == 8) return "AUG";
        if(month == 9) return "SEP";
        if(month == 10) return "OCT";
        if(month == 11) return "NOV";
        if(month == 12) return "DEC";
        return "JAN";
    }

    // display second date picker dialog
    public void openDatePicker2(View view) {
        datePickerDialog2.show();
    }
}