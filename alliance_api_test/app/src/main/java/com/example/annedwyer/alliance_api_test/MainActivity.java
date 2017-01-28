package com.example.annedwyer.alliance_api_test;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button b2;
    Button b3;
    Button b4;
    private RetrieveFeedTask newTask;
    public static String dataFromAsyncTask;
    private DBHandler db;
    private SimpleCursorAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = new DBHandler(this);
        RetrieveFeedTask myTask = new RetrieveFeedTask();
        myTask.execute("https://www.thebluealliance.com/api/v2/event/2016txsa/matches");
        setContentView(R.layout.activity_main);
        findViewById(R.id.getApi).setOnClickListener(this);
        findViewById(R.id.erase).setOnClickListener(this);
        findViewById(R.id.export).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getApi:
                addMatch();
                Toast.makeText(getApplicationContext(), "Data imported", Toast.LENGTH_LONG).show();
                displayListView();
                break;
            case R.id.erase:
                db.deleteAllMatches();
                Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_LONG).show();
                displayListView();
                break;
            case R.id.export:
                db.exportMatchDataToCSV();
                Toast.makeText(getApplicationContext(), "Data exported", Toast.LENGTH_LONG).show();


        }
    }


    private void addMatch() {
        Toast.makeText(getApplicationContext(), "Data imported", Toast.LENGTH_LONG).show();
        //Extract info from Json and put into database
        try {
            JSONArray jArray = new JSONArray(dataFromAsyncTask);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject c = jArray.getJSONObject(i);
                String cl = c.getString("comp_level");
                //We only want data from qualifying matches.
                if (cl.equals("qm")) {
                    String mn = c.getString("match_number");
                    JSONObject al = c.getJSONObject("alliances");
                    JSONObject alb = al.getJSONObject("blue");
                    JSONObject alr = al.getJSONObject("red");
                    String bteam = alb.getString("teams");
                    String rteam = alr.getString("teams");

                    String[] blueTeam = bteam.split("\"frc");

                    String[] redTeam = rteam.split("\"frc");
                    int a = 0;
                    int b = 0;
                    //These are the loops that generate the values for the database
                    for (int j = 0; j < blueTeam.length; j++) {
                        Log.d("blue_team_length", "Value " + blueTeam.length);

                        String[] tb1 = blueTeam[j].split("\"");
                        for (int jj = 0; jj < tb1.length; jj++) {
                            Log.d("blueTeam_l2", "Value " + tb1.length);
                            Log.d("Index_Value", "Value " + jj);


                            if (!(tb1[jj].equals("[") || tb1[jj].equals(",") || tb1[jj].equals("]"))) {
                                a = a + 1;
                                Log.d("value_a", "Value " + a);

                                String match_num = mn;
                                Log.d("Match_Number", "Value " + mn);
                                String alliance = "blue";
                                String tablet_id = "Blue" + a;
                                Log.d("Tablet_id", "Value " + tablet_id);
                                String team_num = tb1[jj];
                                Log.d("Team_number", "Value "  + tb1[jj]);
                                try {

                                    Matchdata m = new Matchdata(match_num, alliance, tablet_id, team_num);
                                    db.addMatch(m);
                                }catch(Exception e){
                                    Log.e("ERROR", e.getMessage(), e);
                                }
                                db.close();
                            }


                        }


                    }

                    for (int k = 0; k < redTeam.length; k++) {

                        String[] tr1 = redTeam[k].split("\"");
                        for (int kk = 0; kk < tr1.length; kk++) {


                            if (!(tr1[kk].equals("[") || tr1[kk].equals(",") || tr1[kk].equals("]"))) {

                                String match_num = mn;
                                String alliance = "red";
                                String tablet_id = "Red" + b;
                                String team_num = tr1[kk];
                                try {

                                    Matchdata m = new Matchdata(match_num, alliance, tablet_id, team_num);
                                    db.addMatch(m);
                                    db.close();
                                }catch(Exception e){
                                    Log.e("ERROR", e.getMessage(), e);
                                }
                            }
                            b = b + 1;
                        }
                    }
                }
            }


            //TextView tv = (TextView)findViewById(R.id.lineCode);
            //tv.setText(dataFromAsyncTask);


        } catch (final JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

    }

    private void displayListView() {


        Cursor cursor = db.fetchAllMatches();

        // The desired columns to be bound
        String[] columns = new String[]{
                "match_num",
                "alliance",
                "tablet_id",
                "team_num"
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.mnum,
                R.id.clr,
                R.id.tabid,
                R.id.tnum,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.match_data,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the match number from this row in the database.
                String matchNumber =
                        cursor.getString(cursor.getColumnIndexOrThrow("mnum"));
                Toast.makeText(getApplicationContext(),
                        matchNumber, Toast.LENGTH_SHORT).show();

            }
        });
    }
}


