package me.chaig.mpd.mpdearthquakes;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import me.chaig.mpd.mpdearthquakes.models.Earthquake;
import me.chaig.mpd.mpdearthquakes.models.Mapview;

public class RSSFeed extends AsyncTask<Void,Void,Void> {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    public Context context = null;

    private ArrayList<Earthquake> earthquakes = new ArrayList<>();

    /**
     * @param context - context of main activity
     */
    public RSSFeed(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        final MainActivity mainActivity = (MainActivity) this.context;
        final RSSFeed feed = this;
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.onInit(feed);
            }
        });

        //displays all the earthquakes on the map.
        new Mapview().displayAll((Activity) context,this);


    }

    @Override
    protected Void doInBackground(Void... voids) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessDocument();
            }
        }).start();



        return null;
    }

    /*
      This method processes the feed from the url and locates the description tag inside the item node
      and parses the data into and Earthquake object.
     */

    private void ProcessDocument() {

            try {
                String inputLine = "";
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                URLConnection connection = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                boolean inside = false;
                while ((inputLine = in.readLine()) != null) {

                    if(inputLine.contains("<item>")){
                        inside = true;
                    }else if(inputLine.contains("<description>") && inside){
                        Earthquake earthquake = new Earthquake();
                        inputLine = inputLine.replace("<description>","");
                        inputLine = inputLine.replace("</description>","");
                        earthquake.init(inputLine.trim());
                        this.earthquakes.add(earthquake);


                    }

                }
                in.close();
            } catch (IOException ae) {
                ae.printStackTrace();
            }


    }


    public ArrayList<Earthquake> getEarthquakes() {
        return earthquakes;
    }

}
