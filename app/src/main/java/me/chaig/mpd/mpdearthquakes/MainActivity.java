package me.chaig.mpd.mpdearthquakes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;

import me.chaig.mpd.mpdearthquakes.listeners.SortSelector;
import me.chaig.mpd.mpdearthquakes.models.Network;

public class MainActivity extends AppCompatActivity {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checks if the there is an internet connection
        if(new Network().isNetworkAvailable(this)){

            //if there is a internet connection then it will execute the feed and then display the map.
            //And will continue to run every 30 minutes till the application is closed
            final Context context = this;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {


                                    final RSSFeed feed = new RSSFeed(context);
                                    feed.execute();




                }
            },0,1800000);

        }else{

            //This will display an alert dialog if there is no internet connection detect.
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Please connect to a stable internet connection before launching this application")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }




    }

    /**
     *
     * @param feed - The eartquake rss feed.
     */
    /*
        This method fills the spinner with the options available and also add the listener to each item.
     */
    public void onInit(final RSSFeed feed){

        final Activity activity = this;
        final Spinner spinner = activity.findViewById(R.id.sortby);
        String[] dates = new String[]{"Nothing","Date","Time","Magnitude","Depth","Location","Most Northern","Most Eastern","Most Southern","Most Western","Highest Magnitude","Deepest Earthquake","Shallowest Earthquake"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SortSelector(activity,feed));

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


}
