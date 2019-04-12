package me.chaig.mpd.mpdearthquakes.models;

import android.app.Activity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.chaig.mpd.mpdearthquakes.R;
import me.chaig.mpd.mpdearthquakes.RSSFeed;
import me.chaig.mpd.mpdearthquakes.listeners.MarkerSelectListener;

public class Mapview {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */


    /**
     *
     * @param activity - The activity that contains the mapview
     * @param feed - The feed that contains the Earthquakes.
     */
    /*
      This method displays all the earthquakes on the map that is received from the parser.
     */
    public void displayAll(final Activity activity, final RSSFeed feed){

        final MapView mapView = activity.findViewById(R.id.mapView);

        mapView.onCreate(activity.getIntent().getExtras());

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                googleMap.setBuildingsEnabled(true);
                LatLng coordinates = new LatLng(54.50,-2.48);
                for(Earthquake e : feed.getEarthquakes()){

                    LatLng point = new LatLng(Double.parseDouble(e.getLat()),Double.parseDouble(e.getLong()));
                    googleMap.addMarker(new MarkerOptions().position(point).title("Date: " + e.getDate()).snippet("Time: "+ e.getTime()));
                    googleMap.setOnMarkerClickListener(new MarkerSelectListener(activity,feed));

                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 5));
                mapView.onResume();
            }
        });
    }

    /**
     *
     * @param activity -  The activity that contains the map
     * @param Lat - The latitude of the earthquake
     * @param Long - The longitude of the earthquake
     */

    /*
        This method displays a single earthquake based on the latitude and longitude
     */
    public void displaySelected(final Activity activity, final String Lat, final String Long){
        final MapView mapView = activity.findViewById(R.id.detailView);

        mapView.onCreate(activity.getIntent().getExtras());

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                LatLng point = new LatLng(Double.parseDouble(Lat),Double.parseDouble(Long));
                googleMap.addMarker(new MarkerOptions().position(point));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
                mapView.onResume();
            }
        });
    }


    /**
     *
     * @param activity -  The activity that contains the map
     * @param earthquakes - The list of earthquakes you want to mark on the map
     */
    /*
       This method loops through all the earthquakes passed in and displays a marker at each location
     */
    public void displayQuery(final Activity activity, final ArrayList<Earthquake> earthquakes){
        final MapView mapView = activity.findViewById(R.id.mapView);

        mapView.onCreate(activity.getIntent().getExtras());

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                LatLng coordinates = new LatLng(54.50,-2.48);
                for(Earthquake e : earthquakes){
                    LatLng point = new LatLng(Double.parseDouble(e.getLat()),Double.parseDouble(e.getLong()));
                    googleMap.addMarker(new MarkerOptions().position(point).title("Date: " + e.getDate()).snippet("Time: "+ e.getTime()));
                    googleMap.setOnMarkerClickListener(new MarkerSelectListener(activity,earthquakes));
                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 5));
                mapView.onResume();
            }
        });
    }

}
