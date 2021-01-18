package com.example.asmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button addData,showGraph;
    DBHelper db;
    UserCursorAdapter displayAdapter;
    Cursor displayCur;

    public static final String DBNAME = "asmoAppDB.db" ;
    public static final int DB_VERSION= 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this,DBNAME,null,DB_VERSION);
        addData = findViewById(R.id.addDataMainActivity);
        showGraph = findViewById(R.id.showChartMainActivity);

        showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showGraphActivity = new Intent(getApplicationContext(),GraphActivity.class);
                startActivity(showGraphActivity);
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDataActivity = new Intent(getApplicationContext(),AddDataActivity.class);
                startActivity(addDataActivity);
            }
        });

        db = new DBHelper(this,DBNAME,null,DB_VERSION);
        ListView detailList = findViewById(R.id.detailsListMainActivity);
        displayCur = db.display();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                displayAdapter = new UserCursorAdapter(getApplicationContext(), displayCur);
                detailList.setAdapter(displayAdapter);
            }
        });

        detailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if(cursor != null){
                    Integer data = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    Intent details = new Intent(getApplicationContext(),Details.class);
                    details.putExtra("id",data);
                    startActivity(details);
                }
            }
        });



    }
}