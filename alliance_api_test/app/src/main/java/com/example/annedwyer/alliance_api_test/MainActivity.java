package com.example.annedwyer.alliance_api_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



/*
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;



        protected String doInBackground(Void... urls) {
           // String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://www.thebluealliance.com/api/v2/event/2010sc/matches");
                HttpURLConnection yc = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    in.close();
                    return stringBuilder.toString();
                }
                finally{
                    yc.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e );
                return null;
            }
        }
    }
    */
    public static class URLConnectionReader {
        public static void main(String[] args) throws Exception {
            URL bluealliance = new URL("https://www.thebluealliance.com/api/v2/event/2010sc/matches");
            URLConnection yc = bluealliance.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()
                    )
            );

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }
}
}


