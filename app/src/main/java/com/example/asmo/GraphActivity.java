package com.example.asmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class GraphActivity extends AppCompatActivity {
    LineChart lineChart;
    DBHelper db;
    Cursor displayCur;
    int sum=0,num=0;
    ImageButton back;
    TextView avg;

    public static final String DBNAME = "asmoAppDB.db";
    public static final int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        back = findViewById(R.id.backHomePageStats);
        lineChart = findViewById(R.id.reportingChart);
        avg = findViewById(R.id.statsAvg);
        LineDataSet lineDataSet = new LineDataSet(dataValues1(),"DataSet 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homePage);
            }
        });
        displayCur = db.returnFlow();
        while (displayCur.moveToNext()){
            sum = sum + displayCur.getInt(displayCur.getColumnIndexOrThrow("peakFlow"));
            ++num;
        }
        sum = sum/num;
        avg.setText(Integer.toString(sum));

    }

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        db = new DBHelper(this, DBNAME, null, DB_VERSION);
        displayCur = db.returnFlow();
        int i=0;
        while (displayCur.moveToNext()) {
            dataVals.add(new Entry(++i, displayCur.getInt(displayCur.getColumnIndexOrThrow("peakFlow"))));
        }
        return dataVals;
    }

}