package com.example.madhumita.navi;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by madhumita on 7/18/2016.
 */
public class PlacesDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

    JSONObject googlePlacesJson;
    GoogleMap googleMap;
    String loc;
    View v;
    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

        List<HashMap<String, String>> googlePlacesList = null;
        Places placeJsonParser = new Places();

        try {
            googleMap = (GoogleMap) inputObj[0];
            googlePlacesJson = new JSONObject((String) inputObj[1]);
            loc=(String) inputObj[2];
            v=(View) inputObj[3];
            Log.e("In Place","val"+loc);
            googlePlacesList = placeJsonParser.parse(googlePlacesJson);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }
//Check if API's are enabled
    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {
        googleMap.clear();
        List<ArrayList<String>> Ratingname = new ArrayList<ArrayList<String>>();
        try {
            if(list.size()==0) {
                Snackbar.make(v, "Enable Places API", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })

                        .show();

            }
            int count=0;
            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = list.get(i);
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                String rating = googlePlace.get("rating");

                Ratingname.add(new ArrayList<String>(Arrays.asList(rating, placeName)));
                //  Log.e(Ratingname.toString());
                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                // markerOptions.title(placeName + " : " + vicinity);
                markerOptions.title(placeName);//put the cafe on the map

                //if(pl     aceName.equalsIgnoreCase(loc)) {]


                Log.e("Place" + placeName.toLowerCase() + loc.toLowerCase(), "loc:" + placeName.toLowerCase().contains(loc.toLowerCase()));
                if (!loc.toLowerCase().equals("") && placeName.toLowerCase().contains(loc.toLowerCase())) {
                    //Log.e("In loop", "Vrify");
                    googleMap.addMarker(markerOptions)
                            .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(markerOptions).showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
// mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    // Zoom in the Google Map
                    count++;
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

                } else {
                    googleMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                }

            }
            if(count==0)
            { Snackbar.make(v, "No Match found for the search", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })

                    .show();

            }
        }
        catch (Exception e){

        }






    }
}

