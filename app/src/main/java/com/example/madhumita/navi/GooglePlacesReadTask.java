package com.example.madhumita.navi;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by madhumita on 7/18/2016.
 */
public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    GoogleMap googleMap;
    String loc;
    View v;

    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            googleMap = (GoogleMap) inputObj[0];
            String googlePlacesUrl = (String) inputObj[1];
            loc=(String) inputObj[2];
            v=(View)inputObj[3];
            Log.d("Google Place Read Task", loc);
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }
//Capture the result using Object
    @Override
    protected void onPostExecute(String result) {
        PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
        Object[] toPass = new Object[4];
        toPass[0] = googleMap;
        toPass[1] = result;
        toPass[2]=loc;
        toPass[3] = v;
        placesDisplayTask.execute(toPass);
    }
}
