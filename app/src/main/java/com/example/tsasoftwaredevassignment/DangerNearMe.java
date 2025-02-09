package com.example.tsasoftwaredevassignment;

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

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private DatePickerDialog datePickerDialog2;

    private Button dateButton2;

        Spinner spinner;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_danger_near_me);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            initDatePicker();
            initDatePicker2();
            dateButton = findViewById(R.id.datePickerButton);
            dateButton.setText(getTodayDate());

            dateButton2 = findViewById(R.id.datePickerButton2);
            dateButton2.setText(getTodayDate2());


            spinner =findViewById(R.id.spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_items, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = 2;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month)
    {
        if (month == 1) {
            return "JAN";
        } else if (month == 2) {
            return "FEB";
        } else if (month == 3) {
            return "MAR";
        } else if (month == 4) {
            return "APR";
        } else if (month == 5) {
            return "MAY";
        } else if (month == 6) {
            return "JUN";
        } else if (month == 7) {
            return "JUL";
        } else if (month == 8) {
            return "AUG";
        } else if (month == 9) {
            return "SEP";
        }else if (month == 10) {
            return "OCT";
        }else if (month == 11) {
            return "NOV";
        } else if (month == 12) {
            return "DEC";
        } else{
            return "JAN";
        }
    }



    public void openDatePicker(View view)
            {
                datePickerDialog.show();
            }
    public void launchRecent(View v){
        //launchDangerNearME
        Intent r = new Intent(this, Dates.class);
        String startDate = new String("2019-01");
        String endDate = new String("2024-12");
        @SuppressLint("ResourceType") String county = (String) getText(R.id.spinner);
        r.putExtra("startDate", startDate);
        r.putExtra("endDate", endDate);
        r.putExtra("county", county);
        startActivity(r);
    }
    public void launchAllTime(View v) {
        //launchDangerNearME
        Intent a = new Intent(this, Dates.class);
        @SuppressLint("ResourceType") String county = (String) getText(R.id.spinner);
        String startDate = new String("1900-01");
        String endDate = new String("2024-12");
        a.putExtra("county",county);
        a.putExtra("startDate", startDate);
        a.putExtra("endDate", endDate);
        startActivity(a);
    }

    @SuppressLint("ResourceType")
    public void launchCustom(View v) {
        //launchDangerNearME
        Intent a = new Intent(this, Dates.class);
        String startDate;
        String endDate;
        Button start = findViewById(R.id.datePickerButton);
        Button end = findViewById(R.id.datePickerButton2);
        startDate = start.getText().toString();
        endDate = end.getText().toString();
        @SuppressLint("ResourceType")
        String county = (String) getText(R.id.spinner);
        a.putExtra("startDate", startDate);
        a.putExtra("endDate", endDate);
        a.putExtra("county", county);
        startActivity(a);
    }
    private String getTodayDate2()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString2(day, month, year);
    }

    private void initDatePicker2()
    {
        DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker2, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString2(day, month, year);
                dateButton2.setText(date);
                dateButton2.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = 2;

        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener2, year, month, day);
    }

    private String makeDateString2(int day, int month, int year)
    {
        return getMonthFormat2(month) + " " + day + " " + year;
    }
    private String getMonthFormat2(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "JAN";
    }
    public void openDatePicker2(View view)
    {
        datePickerDialog2.show();
    }
}