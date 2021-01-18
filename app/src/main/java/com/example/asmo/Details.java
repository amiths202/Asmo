package com.example.asmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView date,time,flow,medication,comment;
    Integer data;
    ImageButton back,edit,delete;
    DBHelper db;
    Cursor displayCur;
    String sdate,stime,sflow,smedication,scomment;

    public static final String DBNAME = "asmoAppDB.db" ;
    public static final int DB_VERSION= 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = new DBHelper(this,DBNAME,null,DB_VERSION);
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            data = extra.getInt("id");
        }
        //Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
        displayCur = db.displayOne(data);
        date = findViewById(R.id.dateDetailsTextView);
        time = findViewById(R.id.timeDetailsTextView);
        flow = findViewById(R.id.flowDetailsTextView);
        medication = findViewById(R.id.medicationDetailsTextView);
        comment = findViewById(R.id.commentDetailsTextView);
        back = findViewById(R.id.backDetailsTextView);
        edit = findViewById(R.id.editDetailsButton);
        delete = findViewById(R.id.deleteDetailsButton);

        sdate = displayCur.getString(displayCur.getColumnIndexOrThrow("date"));
        stime = displayCur.getString(displayCur.getColumnIndexOrThrow("time"));
        sflow = displayCur.getString(displayCur.getColumnIndexOrThrow("peakFlow"));
        scomment = displayCur.getString(displayCur.getColumnIndexOrThrow("comment"));
        smedication =displayCur.getString(displayCur.getColumnIndexOrThrow("medication"));
        date.setText(sdate);
        time.setText(stime);
        flow.setText(sflow);
        comment.setText(scomment);
        medication.setText(smedication);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homePage);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(data);
                Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                Intent homePage = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homePage);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editPage = new Intent(getApplicationContext(),EditActivity.class);
                editPage.putExtra("id",data);
                startActivity(editPage);
            }
        });

    }
}