package me.chaig.mpd.mpdearthquakes.listeners;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.chaig.mpd.mpdearthquakes.MarkerDetailView;
import me.chaig.mpd.mpdearthquakes.RSSFeed;
import me.chaig.mpd.mpdearthquakes.models.Earthquake;

public class MarkerSelectListener implements GoogleMap.OnMarkerClickListener {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    private RSSFeed feed;
    private Activity activity;
    private ArrayList<Earthquake> earthquakes;
    private boolean doubleClicked = false;

    /**
     *
     * @param activity -  The activity of the view that is getting edited
     * @param feed - The feed of earthquakes.
     */
    public MarkerSelectListener(Activity activity, RSSFeed feed){
        this.feed = feed;
        this.activity = activity;
    }

    /**
     *
     * @param activity -  The activity of the view that is getting edited
     * @param earthquakes -  The array list of earthquakes that are to be displayed after being clicked.
     */
    public MarkerSelectListener(Activity activity, ArrayList<Earthquake> earthquakes){
        this.earthquakes = earthquakes;
        this.activity = activity;
    }



    /*
        When a marker has been clicked it will get the earthquake that corresponds to that id
        and opens the new view with that information and displays it.
     */
    /**
     *
     * @param marker -  The marker that has been clicked.
     * @return marker click
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerID = getID(marker);
        Earthquake earthquake;
        if(feed == null){
            final ArrayList<Earthquake> earthquakes = this.earthquakes;
            earthquake = earthquakes.get(getID(marker));
        }else{
            earthquake = getEarthquakeFromMarkerId(markerID);
        }

        if(!doubleClicked){
            doubleClicked = true;
            marker.showInfoWindow();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    doubleClicked = false;

                }
            },1500);
        }else{

                    Intent intent = new Intent(activity, MarkerDetailView.class);
                    intent.putExtra("Location",earthquake.getLocation());
                    intent.putExtra("Date",earthquake.getDate());
                    intent.putExtra("Time",earthquake.getTime());
                    intent.putExtra("Depth",earthquake.getDepth());
                    intent.putExtra("Magnitude",earthquake.getMagnitude());
                    intent.putExtra("Lat",earthquake.getLat());
                    intent.putExtra("Long",earthquake.getLong());
                    activity.startActivity(intent);

            doubleClicked = false;
        }

        return true;
    }

    /**
     *
     * @param marker The marker that is being clicked on
     * @return the id of the marker excluding the "m"
     */
    private int getID(Marker marker){
        return Integer.parseInt(marker.getId().split("m")[1]);
    }

    /**
     *
     * @param id - The id of the marker that is clicked on.
     * @return The earthquake corresponding to the id.
     */
    private Earthquake getEarthquakeFromMarkerId(int id){
        return feed.getEarthquakes().get(id);
    }
}
