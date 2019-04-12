package me.chaig.mpd.mpdearthquakes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import me.chaig.mpd.mpdearthquakes.models.Mapview;

public class MarkerDetailView extends AppCompatActivity {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detail_view);

        /*
          This grabs all the earthquake data that is stored in the bundle and sets it to the corresponding text view.
         */

        TextView Location = this.findViewById(R.id.location);
        TextView Date = this.findViewById(R.id.Date);
        TextView Time = this.findViewById(R.id.Time);
        TextView Depth = this.findViewById(R.id.Depth);
        TextView Magnitude = this.findViewById(R.id.Magnitude);

        Bundle b = getIntent().getExtras();


        Location.setText(b.getString("Location"));
        Date.setText(b.getString("Date"));
        Time.setText(b.getString("Time"));
        Depth.setText(b.getString("Depth"));
        Magnitude.setText(b.getString("Magnitude"));

        String Lat = b.getString("Lat");
        String Long = b.getString("Long");

        //Displays the earthquake using the lat and long.

        new Mapview().displaySelected(this,Lat,Long);
    }
}
