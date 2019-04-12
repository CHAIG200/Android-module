package me.chaig.mpd.mpdearthquakes.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import me.chaig.mpd.mpdearthquakes.RSSFeed;
import me.chaig.mpd.mpdearthquakes.models.Earthquake;
import me.chaig.mpd.mpdearthquakes.models.Mapview;

public class SearchQueryListener implements View.OnClickListener {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    private Object[] Objects;
    private String Query;
    private RSSFeed feed;

    /**
     *
     * @param feed - The RssFeed that gathers the earthquakes
     * @param Query - The query of what earthquakes you want back
     * @param objects - the inputs of the query.
     */
    public SearchQueryListener(RSSFeed feed, String Query, Object[] objects){

        this.Objects = objects;
        this.Query = Query;
        this.feed = feed;
    }


    /*
        This method takes in the query that was selected and performs an algorithm to
        determine which earthquakes should be displayed based on the inputs that where given.
     */
    @Override
    public void onClick(View view) {
        final Activity activity = (Activity) feed.context;
        switch (Query){

            case "Location":
                //This displays all earthquakes that have the same location to the location passed in
                String location = ((Spinner)Objects[0]).getSelectedItem().toString();
                ArrayList<Earthquake> earthquakes = new ArrayList<>();
                for(Earthquake earthquake : feed.getEarthquakes()){

                    if(earthquake.getLocation().equals(location)){
                        earthquakes.add(earthquake);
                    }

                }

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Date":
                //This displays all earthquakes that happened between the two dates that are passed in.
                String startDate = ((EditText)Objects[0]).getText().toString();
                String endDate = ((EditText)Objects[1]).getText().toString();
                earthquakes = new ArrayList<>();
                for(Earthquake earthquake : feed.getEarthquakes()){

                    try {

                        Date start = new SimpleDateFormat("dd MMM yyyy").parse(startDate);
                        Date end = new SimpleDateFormat("dd MMM yyyy").parse(endDate);
                        Date query = new SimpleDateFormat("dd MMM yyyy").parse(earthquake.getDate().substring(4,earthquake.getDate().length()));
                        if(query.after(start) && query.before(end)){
                        earthquakes.add(earthquake);
                        }
                    } catch (ParseException e) {
                        displayFieldErrorToast(activity);
                    }
                }
                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Time":
                //This displays all earthquakes that happened on the date specified and between the two times passed in.
                String selectedDate = ((EditText)Objects[0]).getText().toString();
                String startTime = ((EditText)Objects[1]).getText().toString();
                String endTime = ((EditText)Objects[2]).getText().toString();
                earthquakes = new ArrayList<>();
                for(Earthquake earthquake : feed.getEarthquakes()){

                    try {

                        try{
                            Date date = new SimpleDateFormat("dd MMM yyyy").parse(selectedDate);
                            Date query = new SimpleDateFormat("dd MMM yyyy").parse(earthquake.getDate().substring(4,earthquake.getDate().length()));
                            if(query.getTime() == date.getTime()){

                                int startHour  = Integer.parseInt(startTime.split(":")[0]);
                                int startMinute  = Integer.parseInt(startTime.split(":")[1]);
                                int endHour  = Integer.parseInt(endTime.split(":")[0]);
                                int endMinute  = Integer.parseInt(endTime.split(":")[1]);

                                int queryHour = Integer.parseInt(earthquake.getTime().split(":")[0]);
                                int queryMinute = Integer.parseInt(earthquake.getTime().split(":")[1]);

                                if(queryHour == startHour){
                                    if(queryMinute >= startMinute){
                                        earthquakes.add(earthquake);
                                    }
                                }else if(queryHour == endHour){
                                    if(queryMinute <= endMinute){
                                        earthquakes.add(earthquake);
                                    }
                                }else if(queryHour > startHour && queryHour < endHour){
                                    earthquakes.add(earthquake);
                                }

                            }
                        }catch (NumberFormatException nfe){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getApplicationContext(),"Please make sure all fields are filled in.",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    } catch (ParseException e) {
                        displayFieldErrorToast(activity);

                    }
                }

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }
                break;

            case "Magnitude":
                //This displays all earthquakes that have a magnitude between the two passed in.
                String startMagnitude = ((EditText)Objects[0]).getText().toString();
                String endMagnitude = ((EditText)Objects[1]).getText().toString();
                earthquakes = new ArrayList<>();
                for(Earthquake earthquake : feed.getEarthquakes()){


                        try{

                            double start  = Double.parseDouble(startMagnitude);
                            double end  = Double.parseDouble(endMagnitude);



                                double eqMagnitude = Double.parseDouble(earthquake.getMagnitude());

                                if(start > end || end < start){
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity.getApplicationContext(),"Please make sure all fields are filled in correctly.",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    if(eqMagnitude > start && eqMagnitude < end){
                                        earthquakes.add(earthquake);
                                    }
                                }





                        }catch (NumberFormatException nfe){
                            displayFieldErrorToast(activity);
                        }



                }

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Depth":
                //This displays all earthquakes that have a depth between the two passed in.
                String startDepth = ((EditText)Objects[0]).getText().toString();
                String endDepth = ((EditText)Objects[1]).getText().toString();
                earthquakes = new ArrayList<>();
                for(Earthquake earthquake : feed.getEarthquakes()){
                    try{

                        double shallow  = Double.parseDouble(startDepth);
                        double deep  = Double.parseDouble(endDepth);
                        double eqDepth = Double.parseDouble(earthquake.getDepth().split("km")[0]);
                        Log.d("test",eqDepth + "");

                        if(shallow > deep || deep < shallow ){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getApplicationContext(),"Please make sure all fields are filled in correctly.",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            if(eqDepth >= shallow && eqDepth <= deep){
                                earthquakes.add(earthquake);
                            }
                        }



                    }catch (NumberFormatException nfe){
                        displayFieldErrorToast(activity);
                    }



                }

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Most Northern":
                //This displays the most northern earthquake on the date specified.
                EditText editText = (EditText) Objects[0];
                String date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double highestLatitude = -90;
                Earthquake North = null;

                for(Earthquake earthquake : feed.getEarthquakes()){
                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getLat()) > highestLatitude){
                            highestLatitude = Double.parseDouble(earthquake.getLat());
                            North = earthquake;
                        }
                    }
                }

                if(North != null) earthquakes.add(North);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Most Eastern":
                //This displays the most eastern earthquake on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double lowestLongitude = 180;
                Earthquake East = null;
                for(Earthquake earthquake : feed.getEarthquakes()){
                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getLong()) < lowestLongitude){
                            lowestLongitude = Double.parseDouble(earthquake.getLong());
                            East = earthquake;
                        }
                    }

                }

                if(East != null) earthquakes.add(East);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }
                break;

            case "Most Southern":
                //This displays the most southern earthquake on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double lowestLatitude = 90;
                Earthquake South = null;
                for(Earthquake earthquake : feed.getEarthquakes()){
                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getLat()) < lowestLatitude){
                            lowestLatitude = Double.parseDouble(earthquake.getLat());
                            South = earthquake;
                        }
                    }

                }

                if(South != null) earthquakes.add(South);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Most Western":
                //This displays the most western earthquake on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double highestLongitude = -180;
                Earthquake West = null;
                for(Earthquake earthquake : feed.getEarthquakes()){
                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getLong()) > highestLongitude){
                            highestLongitude = Double.parseDouble(earthquake.getLong());
                            West = earthquake;
                        }
                    }

                }

                if(West != null) earthquakes.add(West);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }


                break;

            case "Highest Magnitude":
                //This displays the earthquake with the highest magnitude on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double largestMagnitude = -10.00;
                Earthquake LargestMagnitude = null;
                for(Earthquake earthquake : feed.getEarthquakes()){
                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getMagnitude()) > largestMagnitude){
                            largestMagnitude = Double.parseDouble(earthquake.getMagnitude());
                            LargestMagnitude = earthquake;
                        }
                    }

                }

                if(LargestMagnitude != null) earthquakes.add(LargestMagnitude);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Deepest Earthquake":
                //This displays the deepest earthquake on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double deepestDepth = 0;
                Earthquake Deepest = null;
                for(Earthquake earthquake : feed.getEarthquakes()){

                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getDepth().split("\\s+")[0]) > deepestDepth){
                            deepestDepth = Double.parseDouble(earthquake.getDepth().split("\\s+")[0]);
                            Deepest = earthquake;
                        }
                    }
                }

                if(Deepest != null) earthquakes.add(Deepest);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }

                break;

            case "Shallowest Earthquake":
                //This displays the shallowest earthquake on the date specified.
                editText = (EditText) Objects[0];
                date = editText.getText().toString();
                earthquakes = new ArrayList<>();
                double shllowestDepth =  100;
                Earthquake Shallowest = null;
                for(Earthquake earthquake : feed.getEarthquakes()){

                    if(date.equals(earthquake.getDate().substring(5,earthquake.getDate().length()))){
                        if(Double.parseDouble(earthquake.getDepth().split("\\s+")[0]) < shllowestDepth){

                            shllowestDepth = Double.parseDouble(earthquake.getDepth().split("\\s+")[0]);
                            Shallowest = earthquake;
                        }
                    }

                }

                if(Shallowest != null) earthquakes.add(Shallowest);

                if(earthquakes.size() != 0){
                    new Mapview().displayQuery((Activity)feed.context,earthquakes);
                }else{
                    displayNoEearthquakesToast(activity);
                }


                break;

        }

    }

    /**
     *
     * @param activity the activity that you are currently viewing
     */

     /*
        Displays a toast stating that there are no earthquakes to display using the selected query.
     */
    public void displayNoEearthquakesToast(final Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(),"There are no earthquakes to display.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param activity the activity that you are currently viewing
     */
    /*
        Displays a toast stating that one or more fields are not filled in correctly
     */
    private void displayFieldErrorToast(final Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(),"Please make sure all fields are filled in correctly.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
