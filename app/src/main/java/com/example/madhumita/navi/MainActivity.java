package com.example.madhumita.navi;

import android.*;
import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.madhumita.navi.R.id.searchbutton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,OnMapReadyCallback {

    SupportMapFragment mapFragment;
    RelativeLayout rellay;
    private GoogleApiClient client;
    private GoogleMap mMap;

    NavigationView navigationView;
    EditText ed;
    /* set key on google API*/
    private static final String GOOGLE_API_KEY = "AIzaSyBi0TMlhWgF9NGgPVmcteUsiHj8GiC3ztk";
    private static final String SERVER_KEY = "AIzaSyDAqGgcAgjeZocfR7ohlEPKr5Y-WbMxOUk";
    private int PROXIMITY_RADIUS = 5000;//randoom value
    MapView mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        //googleApicheck();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rellay = (RelativeLayout) findViewById(R.id.rel);
        rellay.setVisibility(View.INVISIBLE);
        //


        //


        networkCheck();
        mv = (MapView) findViewById(R.id.mapview);
        mv.onCreate(savedInstanceState);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mv.getMapAsync(this);
        mv.setEnabled(true);
        mapFragment.getMapAsync(this);
Log.e("Build.VERSION.SDK_INT"+Build.VERSION.SDK_INT,"Build.VERSION_CODES.M"+Build.VERSION_CODES.M);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);


                //
                //
                //
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();


            } else {
                showGPSDisabledAlertToUser();
            }
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mv.setEnabled(true);
        mv.onLowMemory();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.setEnabled(true);
        mv.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.setEnabled(true);
        mv.onResume();
        //mv.setEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mv.setEnabled(true);
        mv.onSaveInstanceState( outState);
    }

    private void showGPSDisabledAlertToUser(){
        View parentLayout = findViewById(R.id.rel);
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
                .setAction("Go To Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).setDuration(50000)
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm=getFragmentManager();


        android.support.v4.app.FragmentManager sfm=getSupportFragmentManager();
        mv.setVisibility(View.INVISIBLE);

//        View parentLayout = findViewById(R.id.drawer_layout);
//        ConnectivityManager CM= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo ninfo=CM.getActiveNetworkInfo();
//        if(ninfo ==null && !ninfo.isConnected()) {
//            Snackbar.make(parentLayout, "This is main activity", Snackbar.LENGTH_LONG)
//                    .setAction("CLOSE", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    })
//                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
//                    .show();
//        }
//        else {

            // Handle navigation view item clicks here.
            int id = item.getItemId();
//            if (mapFragment.isAdded())
//                sfm.beginTransaction().hide(mapFragment).commit();

            if (id == R.id.nav_camera) {
                //networkCheck();
                //googleApicheck();
                rellay.setVisibility(View.VISIBLE);
                if (!mapFragment.isAdded())
                    sfm.beginTransaction().add(R.id.map, mapFragment).commit();
                else
                    sfm.beginTransaction().show(mapFragment).commit();

                // sfm.beginTransaction().add(R.id.find,mapFragment).commit();


                // Handle the camera action
            } else if (id == R.id.nav_gallery) {


                Intent i = new Intent(this, sortRating.class);

                startActivity(i);

            } /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        //}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //googlePlaces = new GooglePlaces();
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker in Sydney"));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //


            //
            //
            //
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // mapFragment.getMapAsync(this);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        try {


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !myLocation.equals("")) {
                //Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();


                // set map type
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                // Get latitude of the current location


                double latitude = myLocation.getLatitude();

                // Get longitude of the current location
                double longitude = myLocation.getLongitude();

                // Create a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);

                // Show the current location in Google Map
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                // Zoom in the Google Map
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
                // mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!");
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                //markerOptions.title(myLocation.toString());
                markerOptions.title("Current Location");
                mMap.addMarker(markerOptions).showInfoWindow();
            } else {
                showGPSDisabledAlertToUser();
            }
        } catch (Exception E) {

networkCheck();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){}
                else{
                   Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);}
        }
    }

    public void search(View v){
        //setContentView(R.layout.content_main);
        networkCheck();

        ////
        /////

        //////
        /////
        ed = (EditText) findViewById(R.id.cafe1);
        String namecafe = ed.getText().toString();
        //Log.e("In seacr", "dddd"+namecafe);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
       mapFragment.getMapAsync(this);
        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


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
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
           // Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();

        try{
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
       // Log.e("In seacr","dddd:"+googlePlacesUrl);
            View parentLayout = findViewById(R.id.rel);
        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[4];
        toPass[0] = mMap;
        toPass[1] = googlePlacesUrl.toString();
        toPass[2] = namecafe;
            toPass[3] = parentLayout;
        googlePlacesReadTask.execute(toPass);}
        catch (Exception E){
//            Snackbar.make(v, "Enable GPS to proceed", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            showGPSDisabledAlertToUser();
        }
        }else{
            //Toast.makeText(this, "Enable GPS to Proceed", Toast.LENGTH_SHORT).show();
            showGPSDisabledAlertToUser();
        }


    }
    public void networkCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        View parentLayout = findViewById(R.id.rel);
        // View parentLayout =
        if (networkInfo == null || !networkInfo.isConnected()) {

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
        } else {
            Snackbar.make(parentLayout, "Network Connected", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }
    }


    public void googleApicheck(){



        View parentLayout = findViewById(R.id.rel);
        // View parentLayout =
        try {
            client = new GoogleApiClient.Builder(this).build();
            client.connect();
            Snackbar.make(parentLayout, "Google Maps Api is enabled", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }
        catch (Exception e) {
            e.printStackTrace();
            //Log.e("In Exc","Yay");


            Snackbar.make(parentLayout, "Ensure Play Services are enabled at  console.developer", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).setDuration(50000)
                    .show();
        }

    }



}
