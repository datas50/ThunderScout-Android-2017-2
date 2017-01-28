package com.example.annedwyer.dbexporttocsv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//This is a small example app that creates a database and exports it as a CSV.
// This is a good training exercise.




public class MainActivity extends Activity  implements View.OnClickListener{

    //private static final String DNAME = "demo"; //directory in the tablet where the file is put.
    //private static final String nameFile = "2015migul.txt";
    EditText matchNum;
    EditText teamNum;
    EditText allianceColor;
    EditText tabletNum;
    TextView records_TextView;
    Button btnAdd;
    Button btnDelete;

    private DBHandler db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button)findViewById(R.id.button3);
        btnDelete = (Button)findViewById(R.id.button4);
        matchNum = (EditText)findViewById(R.id.matchNum);
        teamNum = (EditText)findViewById(R.id.teamNum);
        allianceColor = (EditText)findViewById(R.id.allianceColor);
        tabletNum = (EditText)findViewById(R.id.tabletNum);
        Log.d("position", "I got here");
        try {

            db = new DBHandler(this, null, null, 1);
        }catch(Exception e){
            Log.i("exxxx", e.toString());
        }
        if(db != null){
            Log.d("chk", "dbnotnull");
        }else{
            Log.d("chk", "dbnull");
        }

        btnAdd.setOnClickListener(this);

        //user_Input = (EditText)findViewById(R.id.user_Input);
        //findViewById(R.id.user_Input);
        //records_TextView = (TextView)findViewById(R.id.records_TextView);


    }

    public void insert(){
        Log.i("exxxx", "CLİCKED ADD BUTTON");
        String mn = matchNum.getText().toString();
        String tn = teamNum.getText().toString();
        String ac = allianceColor.getText().toString();
        String tid = tabletNum.getText().toString();
        ScoutData d = new ScoutData(mn,tn,ac,tid);
        if(db == null){
            Log.d("message", "dbnull");
            DBHandler db = new DBHandler(this, null, null, 1);
        }
        if(db == null){
            Log.d("message2", "dbnull");
        }

        db.addMatch(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                exportData();
                break;
            case R.id.button3:
                insert();
                break;



        }
    }








    private void exportData(){
        db.exportMatchDataToCSV();
        Toast.makeText(getApplicationContext(), "File exported", Toast.LENGTH_LONG).show();

    }









}


