package com.example.annedwyer.dbexporttocsv;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//This is a small example app that creates a database and exports it as a CSV.
// This is a good training exercise.




public class MainActivity extends Activity implements OnClickListener {

    private static final String SAMPLE_DB_NAME = "TrekBook";
    private static final String SAMPLE_TABLE_NAME = "Info";
    private static final String FILENAME = "dbDemo.csv"; //csv file that is created.
    private static final String DNAME = "demo"; //directory in the tablet where the file is put.
    private static final String nameFile = "2015migul.txt";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                deleteDB();
                break;
            case R.id.button2:
                exportMatchDataToCSV();
                break;
            case R.id.button3:
                createDB();
                break;
            case R.id.button4:
                readJson();
                break;
        }
    }



    private void deleteDB() {
        boolean result = this.deleteDatabase(SAMPLE_DB_NAME);
        if (result == true) {
            Toast.makeText(this, "DB Deleted!", Toast.LENGTH_LONG).show();
        }
    }


    private void createDB() {
        SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                SAMPLE_TABLE_NAME +
                " (LastName VARCHAR, FirstName VARCHAR," +
                " Rank VARCHAR);");
        sampleDB.execSQL("INSERT INTO " +
                SAMPLE_TABLE_NAME +
                " Values ('Kirk','James, T','Captain');");
        sampleDB.close();
        sampleDB.getPath();
        Toast.makeText(this, "DB Created @ " + sampleDB.getPath(), Toast.LENGTH_LONG).show();
    }

// Luke's method (without details) converts the database into a string and then adds it line by line
    // to a file. This particular example hard codes the one database row into a comma separated string.

    private void exportMatchDataToCSV() {
        // Gets the directory for the file. Creates it if it doesn't exist.
        File rootPath = new File(Environment.getExternalStorageDirectory(), DNAME);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        File matchScoutCSVFile;
        matchScoutCSVFile = new File(rootPath, FILENAME); //this is the file names with path.
        String matchHeader = "LastName," + "FirstName," + "Rank\n";//this is the way the header is added.
        String firstLine = "Kirk," + "James," + "Captain\n"; // Takes the one row in the database and makes it a comma separated string.
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(matchScoutCSVFile); //opens a new file
            fileOutputStream.write(matchHeader.getBytes("UTF-8")); //writes header to the file.
            //writes first line to the file. In the app, the code must loop through through each row
            // of the database.
            fileOutputStream.write(firstLine.getBytes("UTF-8"));
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), "DB exported", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readJson(){


        File pathRoot = new File(Environment.getExternalStorageDirectory(), DNAME);


            try {
                File myFile = new File(pathRoot , nameFile);
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                String aDataRow = "";
                String totalData = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    //Find the view by its id

                    totalData += aDataRow + "\n";
            //Set the text

                }
                myReader.close();
                TextView tv = (TextView)findViewById(R.id.lineCode);
                tv.setText(totalData);
                Toast.makeText(getApplicationContext(), "File imported", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();

            }


    }


}


