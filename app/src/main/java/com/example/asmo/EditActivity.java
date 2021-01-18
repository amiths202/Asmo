package com.example.asmo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    Button dateButton, timeButton, dataSubmit;
    EditText dateTextView, timeTextView, peakFlowTextView, commentTextView;
    RadioButton radioMedication,radioMedication1,radioMedication2;
    RadioGroup medicationRadioGroup;
    DBHelper db;
    Integer data;
    Cursor displayCur;
    String sdate,stime,sflow,smedication,scomment;
    String date, time, flow, medication, comment;

    public static final String DBNAME = "asmoAppDB.db";
    public static final int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new DBHelper(this, DBNAME, null, DB_VERSION);
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            data = extra.getInt("id");
        }
        displayCur = db.displayOne(data);
        dateButton = findViewById(R.id.DateEditPage);
        timeButton = findViewById(R.id.TimeEditPage);
        dateTextView = findViewById(R.id.editPageDateEditText);
        timeTextView = findViewById(R.id.editPageTimeEditText);
        peakFlowTextView = findViewById(R.id.editPageNewPeakFlowEditText);
        commentTextView = findViewById(R.id.editPageCommentEditPage);
        medicationRadioGroup = findViewById(R.id.radioGroupMedicationEditPage);
        radioMedication1 = findViewById(R.id.newReadingPostMedicationEditPage);
        radioMedication2 = findViewById(R.id.newReadingPreMedicationEditPage);
        dataSubmit = findViewById(R.id.newReadingSubmitEditPage);
        setDataInPlace();
        dataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = dateTextView.getText().toString();
                time = timeTextView.getText().toString();
                flow = peakFlowTextView.getText().toString();
                radioMedication = findViewById(medicationRadioGroup.getCheckedRadioButtonId());
                medication = radioMedication.getText().toString();
                comment = commentTextView.getText().toString();
                if (db.update(data,date, time, flow, medication, comment)) {
                    Toast.makeText(getApplicationContext(), "Edited Successfully", Toast.LENGTH_SHORT).show();
                    Intent homePage = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homePage);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update. Try again!", Toast.LENGTH_SHORT).show();
                    dateTextView.setText("");
                    timeTextView.setText("");
                    peakFlowTextView.setText("");
                    commentTextView.setText("");
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

    }

    private void setDataInPlace(){
        sdate = displayCur.getString(displayCur.getColumnIndexOrThrow("date"));
        stime = displayCur.getString(displayCur.getColumnIndexOrThrow("time"));
        sflow = displayCur.getString(displayCur.getColumnIndexOrThrow("peakFlow"));
        scomment = displayCur.getString(displayCur.getColumnIndexOrThrow("comment"));
        smedication =displayCur.getString(displayCur.getColumnIndexOrThrow("medication"));
        dateTextView.setText(sdate);
        timeTextView.setText(stime);
        peakFlowTextView.setText(sflow);
        commentTextView.setText(scomment);
        if(radioMedication2.getText() == smedication){
            radioMedication2.setChecked(true);
        }else {
            radioMedication1.setChecked(true);
        }

    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("AddDataActivity", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeTextView.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

    }
}