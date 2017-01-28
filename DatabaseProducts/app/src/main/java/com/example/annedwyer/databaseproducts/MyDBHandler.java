package com.example.annedwyer.databaseproducts;

/**
 * Created by annedwyer on 12/24/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Aybars on 26.09.2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "products.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productName";
    //private static final String DNAME = "demo";
    //private static final String FILENAME = "dbPDemo.csv"; //csv file that is created.


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_PRODUCTNAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_PRODUCTS);
        onCreate(db);
    }

    //Add new row to database
    public void addProduct(Products product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    //Delete product from the database
    public void deleteProduct(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\";");
    }

    //Print out the database as a string
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productName")) != null) {
                dbString += c.getString(c.getColumnIndex("productName"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

/*

    public void exportProductsDataToCSV() {
        //Set up the file to write to
        // Gets the directory for the file. Creates it if it doesn't exist.
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();



        File rootPath = new File(Environment.getExternalStorageDirectory(), DNAME);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        File productsCSVFile;
        productsCSVFile = new File(rootPath, FILENAME); //this is the file names with path.
        String productHeader =  COLUMN_PRODUCTNAME + "\n";//this is the way the header is added.



        try {
            FileOutputStream fileOutputStream = new FileOutputStream(productsCSVFile); //opens a new file
            fileOutputStream.write(productHeader.getBytes("UTF-8")); //writes header to the file.
            //Position after the last row means the end of the results
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("productName")) != null) {
                    dbString += c.getString(c.getColumnIndex("productName"));
                    dbString += "\n";
                    fileOutputStream.write(dbString.getBytes("UTF-8"));
                }
                c.moveToNext();
            }
            db.close();
            fileOutputStream.flush();
            fileOutputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

}