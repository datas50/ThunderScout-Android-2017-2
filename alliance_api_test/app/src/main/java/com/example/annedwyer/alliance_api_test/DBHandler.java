package com.example.annedwyer.alliance_api_test;

/**
 * Created by annedwyer on 1/16/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.database.SQLException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DBHandler extends SQLiteOpenHelper{



    // TODO THis should be put in a constant class but I could't figure out
    // how to access the private variables.
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME ="Test_db";
    // Match table name
    private static final String MATCH_TABLE = "match_info";
    // Match Table Columns names
    private static final String KEY_ID = "_id";
    private static final String COL1 = "match_num";
    private static final String COL4 = "team_num";
    private static final String COL2 = "alliance";
    private static final String COL3 = "tablet_id";
    private static final String FILENAME = "matchDemo.csv"; //csv file that is created.
    private static final String DNAME = "demo"; //directory in the tablet where the file is put.





    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MATCH_TABLE = "CREATE TABLE " + MATCH_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL1 + " TEXT,"
                + COL2 + " TEXT," +  COL3 + " TEXT," + COL4 + " TEXT" +")";
        db.execSQL(CREATE_MATCH_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE);
// Creating tables again
        onCreate(db);
    }



    public long addMatch(Matchdata m) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(COL1, m.getMatch_num());
        initialValues.put(COL2, m.getTeam_num());
        initialValues.put(COL3, m.getAlliance_color());
        initialValues.put(COL4, m.getTablet_id());

        return db.insert(MATCH_TABLE, null, initialValues);
    }

    public long updateMatch(Matchdata m) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(COL1, m.getMatch_num());
        initialValues.put(COL4, m.getTeam_num());
        initialValues.put(COL3, m.getAlliance_color());
        initialValues.put(COL2, m.getTablet_id());

        return db.update(MATCH_TABLE, initialValues, KEY_ID + " = ?",
                new String[]{String.valueOf(m.getId())});
    }

    // Deleting a match row
    public void deleteMatch(Matchdata m) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MATCH_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(m.getId()) });
        db.close();
    }

    public boolean deleteAllMatches() {
        SQLiteDatabase db = this.getWritableDatabase();

        int doneDelete = 0;
        doneDelete = db.delete(MATCH_TABLE, null , null);
        //Put a toast in main activity to say delete is done.
        //Log.w(TAG, Integer.toString(doneDelete));
        db.close();
        return doneDelete > 0;

    }

    public Cursor fetchAllMatches() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.query(MATCH_TABLE, new String[] {KEY_ID,
                        COL1, COL2, COL3, COL4},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Read out the current database to a file

    public void exportMatchDataToCSV() {
        //Set up the file to write to
        // Gets the directory for the file. Creates it if it doesn't exist.
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + MATCH_TABLE + " ORDER BY " + KEY_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        File rootPath = new File(Environment.getExternalStorageDirectory(), DNAME);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        long numRows = DatabaseUtils.queryNumEntries(db, MATCH_TABLE);
        File matchScoutCSVFile;
        matchScoutCSVFile = new File(rootPath, FILENAME); //this is the file names with path.
        String matchHeader =  COL1 + "," + COL2 + "," + COL3 + "," + COL4 + "\n";//this is the way the header is added.
        String rowNext;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(matchScoutCSVFile); //opens a new file
            fileOutputStream.write(matchHeader.getBytes("UTF-8")); //writes header to the file.
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String Col1 = cursor.getString(1);
                String Col2 = cursor.getString(2);
                String Col3 = cursor.getString(3);
                String Col4 = cursor.getString(4);
                String nextRow = Col1 + "," + Col2 + "," + Col3 + "," + Col4 + "\n";
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


