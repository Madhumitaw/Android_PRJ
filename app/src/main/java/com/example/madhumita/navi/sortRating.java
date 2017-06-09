package com.example.madhumita.navi;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class sortRating extends AppCompatActivity {
    private GoogleMap mMap;
    //ListView lv;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adp;
    private RecyclerView.LayoutManager layoutManager;

    EditText ed;
    double latitude = 0.0, longitude = 0.0;
    private int PROXIMITY_RADIUS = 5000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private static final String GOOGLE_API_KEY = "AIzaSyBi0TMlhWgF9NGgPVmcteUsiHj8GiC3ztk";
    private static final String SERVER_KEY = "AIzaSyDAqGgcAgjeZocfR7ohlEPKr5Y-WbMxOUk";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_rating);
        recyclerView = (RecyclerView) findViewById(R.id.rc);
        //showGPSDisabledAlertToUser();
        //lv = (ListView) findViewById(R.id.lv);
        //ListDataAdapter lda=new ListDataAdapter(this,R.layout.row);
        //lv.setAdapter(lda);
        networkCheck();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);

        if (networkCheck())

        {
            try {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !myLocation.equals("")) {
                    // Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
                    double latitude = myLocation.getLatitude();

                    // Get longitude of the current location
                    double longitude = myLocation.getLongitude();
                    StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                    googlePlacesUrl.append("location=" + latitude + "," + longitude);
                    googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
                    googlePlacesUrl.append("&types=" + "cafe");

                    // googlePlacesUrl.append("&sensor=true");
                    //googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
                    googlePlacesUrl.append("&key=" + SERVER_KEY);
                    Log.e("In seacr", "SortLink:" + googlePlacesUrl);
                    GooglePlacesReadTask_rate1 googlePlacesReadTask = new GooglePlacesReadTask_rate1();
                    Object[] toPass = new Object[3];
                    toPass[0] = mMap;
                    toPass[1] = googlePlacesUrl.toString();
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    View parentLayout = findViewById(R.id.rc);
                    String jsonstr = null;
                    googlePlacesReadTask.execute(toPass);
                    String Rating, Name, Vicinity;
                    List<ArrayList<String>> ratingandcafe = new ArrayList<ArrayList<String>>();
                    // List keys = new ArrayList();
                    // List values = new ArrayList();
                    // ListDataAdapter lda=new ListDataAdapter(this,R.layout.row)
                    // ;

                    if (!googlePlacesReadTask.equals("")) {
                        try {


                            jsonstr = googlePlacesReadTask.get().toString();
                            //Log.e("googlePlaces", googlePlacesReadTask.get().toString());

                            try {
                                jsonObject = new JSONObject(jsonstr);
                                jsonArray = jsonObject.getJSONArray("results");
                                int count = 0;
                                //Log.e(":Arraylebgth", "Vale=" + jsonArray.length());
                                if (jsonArray.length() == 0) {
                                    Snackbar.make(parentLayout, "Ensure required API is enabled", Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })

                                            .show();

                                }
                                while (count < jsonArray.length()) {
                                    JSONObject JO = jsonArray.getJSONObject(count);
                                    //JO.
//                    if(JO.getString("rating").equals(""))
//                        Rating="NA";
//                    else
                                    if (JO.has("rating"))
                                        Rating = JO.getString("rating");
                                    else
                                        Rating = "0";
                                    if (JO.has("name"))
                                        Name = JO.getString("name");
                                    else
                                        Name = "NA";
                                    if (JO.has("vicinity"))
                                        Vicinity = JO.getString("vicinity");
                                    else
                                        Vicinity = "NA";

//                    Dataprovider dp = new Dataprovider(Rating,Name);
//                    lda.add(dp);
                                    ratingandcafe.add(new ArrayList<String>(Arrays.asList(Rating, Name, Vicinity)));

                                    //Log.e("count"+count, "JO.getString(\"rating\")"+JO.getString("rating"));
                                    //Log.e("count"+count, "JO.getString(\"name\")"+JO.getString("name"));
                                    count++;


                                }

                                Collections.sort(ratingandcafe, new Comparator<ArrayList<String>>() {
                                    @Override
                                    public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                                        return o1.get(0).compareTo(o2.get(0));
                                    }
                                });

                                Collections.reverse(ratingandcafe);
                                //Log.e("da" + ratingandcafe, "rev");
                                //Log.e("Size","is"+ratingandcafe.size());
                                int arraysize = ratingandcafe.size();
                                String r, n, v;
                                String[] ratings, names, vicinity;
                                ratings = new String[arraysize];
                                names = new String[arraysize];
                                vicinity = new String[arraysize];
                                for (int x = 0; x < ratingandcafe.size(); x++) {
                                    r = ratingandcafe.get(x).get(0);
                                    n = ratingandcafe.get(x).get(1);
                                    v = ratingandcafe.get(x).get(2);
                                    //Dataprovider dp = new Dataprovider(r,n);
                                    //lda.add(dp);
                                    ratings[x] = r;
                                    names[x] = n;
                                    vicinity[x] = v;

                                    //Log.e("Size" + x, "is" + vicinity[x]);
                                }
                                layoutManager = new LinearLayoutManager(this);
                                //Log.e("Valueee", "IS" + layoutManager.toString());
                                adp = new RecyclerAdapter(ratings, names, vicinity);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adp);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        // ATTENTION: This was auto-generated to implement the App Indexing API.
                        // See https://g.co/AppIndexing/AndroidStudio for more information.
                        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
                    }
                } else {
                    showGPSDisabledAlertToUser();
                }
            }
            catch (Exception e){

            }
        }
    }
    private void showGPSDisabledAlertToUser(){
        View parentLayout = findViewById(R.id.rc);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("GPS is disabled in your device and to search GPS is needed Would you like to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Goto Settings Page To Enable GPS",
//                        new DialogInterface.OnClickListener(){
//                            public void onClick(DialogInterface dialog, int id){
//                                Intent callGPSSettingIntent = new Intent(
//                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                startActivity(callGPSSettingIntent);
//                            }
//                        });
//        alertDialogBuilder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener(){
//                    public void onClick(DialogInterface dialog, int id){
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
        Snackbar.make(parentLayout, "Verify Location Settings:Enable Location ", Snackbar.LENGTH_LONG)
                .setAction("Enable Location", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
    public boolean  networkCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        View parentLayout = findViewById(R.id.rc);;
        // View parentLayout =
        if (networkInfo == null || !networkInfo.isConnected()) {
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setMessage("Enable Network Connections")
//                    .setCancelable(false)
//                    .setPositiveButton("Verify Connectivity",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent callGPSSettingIntent = new Intent(
//                                            Settings.ACTION_SETTINGS);
//                                    startActivity(callGPSSettingIntent);
//                                }
//                            });
//            alertDialogBuilder.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = alertDialogBuilder.create();
//            alert.show();

            Snackbar.make(parentLayout, "Ensure Network Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Check Network", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callGPSSettingIntent = new Intent(
                                    Settings.ACTION_SETTINGS);
                            startActivity(callGPSSettingIntent);

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).setDuration(50000)
                    .show();
            return false;
        } else {
            Snackbar.make(parentLayout, "Network Connected", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
            return true;
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "sortRating Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.madhumita.navi/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "sortRating Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.madhumita.navi/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }

    class GooglePlacesReadTask_rate1 extends AsyncTask<Object, Integer, String> {
        String googlePlacesData = null;
        GoogleMap googleMap;

        @Override
        protected String doInBackground(Object... inputObj) {
            try {
                googleMap = (GoogleMap) inputObj[0];
                String googlePlacesUrl = (String) inputObj[1];
                Http http = new Http();
                googlePlacesData = http.read(googlePlacesUrl);
            } catch (Exception e) {
                Log.d("Google Place Read Task", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            PlacesDisplayTask_sort placesDisplayTask = new PlacesDisplayTask_sort();
            Object[] toPass = new Object[2];
            toPass[0] = googleMap;
            toPass[1] = result;
            placesDisplayTask.execute(toPass);
        }
    }

    class PlacesDisplayTask_sort extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

        JSONObject googlePlacesJson;
        GoogleMap googleMap;

        @Override
        protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

            List<HashMap<String, String>> googlePlacesList = null;
            Places placeJsonParser = new Places();

            try {
                googleMap = (GoogleMap) inputObj[0];
                googlePlacesJson = new JSONObject((String) inputObj[1]);
                googlePlacesList = placeJsonParser.parse(googlePlacesJson);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return googlePlacesList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            // googleMap.clear();
            List<ArrayList<String>> Ratingname = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < list.size(); i++) {
                //   MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = list.get(i);
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                String rating = googlePlace.get("rating");

                Ratingname.add(new ArrayList<String>(Arrays.asList(rating, placeName)));
                //  Log.e(Ratingname.toString());
                //LatLng latLng = new LatLng(lat, lng);
                //  markerOptions.position(latLng);
                //markerOptions.title(placeName + " : " + vicinity);
                //googleMap.addMarker(markerOptions);
            }


        }


    }

}
