package com.example.annedwyer.dbexporttocsv;

/**
 * Created by annedwyer on 12/12/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DBHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME ="ScoutingDB.db";
    // Match table name
    private static final String MATCH_TABLE = "match_info";
    // Match Table Columns names
    private static final String KEY_ID = "_id";
    private static final String COL1 = "match_num";
    private static final String COL2 = "team_num";
    private static final String COL3 = "alliance";
    private static final String COL4 = "tablet_id";
    private static final String DNAME = "demo"; //directory in the tablet where the file is put.
    private static final String INPUTFILE = "2015migul.txt";
    private static final String FILENAME = "dbDemo1.csv"; //csv file that is created.

/*
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    */

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MATCH_TABLE = "CREATE TABLE " + MATCH_TABLE + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + COL1 + " TEXT,"
        + COL2 + " TEXT," +  COL3 + " TEXT," + COL4 + " TEXT)";
        db.execSQL(CREATE_MATCH_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE);
// Creating tables again
        onCreate(db);
    }

    public void open() {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    // Adding a new match row
    public void addMatch(ScoutData scoutData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, scoutData.getMatchNum()); // match number
        values.put(COL2, scoutData.getTeamNum()); // team number
        values.put(COL3, scoutData.getAlliance()); // alliance color
        values.put(COL4, scoutData.getTabletId()); // tablet id
// Inserting Row
        db.insert(MATCH_TABLE, null, values);
        db.close(); // Closing database connection
    }


    // Updating a match row
    public int updateMatch(ScoutData match) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, match.getMatchNum()); // match number
        values.put(COL2, match.getTeamNum()); // team number
        values.put(COL3, match.getAlliance()); // alliance color
        values.put(COL4, match.getTabletId()); // tablet id
// updating row
        return db.update(MATCH_TABLE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(match.getId())});
    }

    // Deleting a match row
    public void deleteMatch(ScoutData match) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MATCH_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(match.getId()) });
        db.close();
    }





    //Read out the current database to a file

    public void exportMatchDataToCSV() {
        //Set up the file to write to
        // Gets the directory for the file. Creates it if it doesn't exist.
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + MATCH_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        File rootPath = new File(Environment.getExternalStorageDirectory(), DNAME);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        long numRows = DatabaseUtils.queryNumEntries(db, MATCH_TABLE);
        File matchScoutCSVFile;
        matchScoutCSVFile = new File(rootPath, FILENAME); //this is the file names with path.
        String matchHeader = KEY_ID + "," + COL1 + "," + COL2 + "," + COL3 + "," + COL4 + "\n";//this is the way the header is added.
        String rowNext;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(matchScoutCSVFile); //opens a new file
            fileOutputStream.write(matchHeader.getBytes("UTF-8")); //writes header to the file.
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String Key_Id = cursor.getString(0);
                String Col1 = cursor.getString(1);
                String Col2 = cursor.getString(2);
                String Col3 = cursor.getString(3);
                String Col4 = cursor.getString(4);
                String nextRow = Key_Id + "," + Col1 + "," + Col2 + "," + Col3 + "," + Col4 + "\n";
                fileOutputStream.write(nextRow.getBytes("UTF-8"));
                cursor.moveToNext();

            }
            fileOutputStream.flush();
            fileOutputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }











}
