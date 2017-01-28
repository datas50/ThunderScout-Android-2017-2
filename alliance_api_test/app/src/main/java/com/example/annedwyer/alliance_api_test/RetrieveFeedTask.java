package com.example.annedwyer.alliance_api_test;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
/**
 * Created by annedwyer on 1/2/17.
 */

class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;




    protected String doInBackground(String... urls) {
        // String email = emailText.getText().toString();
        // Do some validation here
        String result = "";

        try {
            URL url = new URL(urls[0]);
            //URL url = new URL("https://www.thebluealliance.com/api/v2/event/2010sc/matches");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.addRequestProperty("X-TBA-App-Id", "frc2834:java-blue-alliance-api:v2.0.2");
            //URL url = new URL("https://www.thebluealliance.com/api/v2/event/2010sc/matches");

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                in.close();
                result = stringBuilder.toString();
                return result;

            }
            finally{
                // in.close();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e );
            return null;
        }


    }


        @Override
        protected void onPostExecute(String result){
            if (result != null){
                try {
                    MainActivity.dataFromAsyncTask = result;
                }catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e );

                }

            }
        }


}


