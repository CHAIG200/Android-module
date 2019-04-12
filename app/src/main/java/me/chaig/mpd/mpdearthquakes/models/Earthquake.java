package me.chaig.mpd.mpdearthquakes.models;

import android.util.Log;

public class Earthquake {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    private String Magnitude;
    private String Lat,Long;
    private String Location;
    private String Depth;
    private String Date;
    private String Time;

    public String getMagnitude() {
        return Magnitude;
    }


    public void setMagnitude(String magnitude) {
        Magnitude = magnitude;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDepth() {
        return Depth;
    }

    public void setDepth(String depth) {
        Depth = depth;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    /**
     *
     * @param description - The description received from the RssFeed
     */
    /*
       This method takes in the date from the description and breaks down the data and splits it and stores it to
       its corresponding variable
    */
    public void init(String description){
        String[] info = description.split(";");
        int x = 0;
        for(String data : info){
            String[] item = data.split(": ");
            switch (x){

                case 0:
                    //Date and time;
                    String date = item[1].substring(0,17).trim();
                    setDate(date);
                    String time = item[1].substring(17, 22).trim();
                    setTime(time);
                    break;
                case 1:
                    //Location
                    setLocation(item[1]);
                    break;
                case 2:
                    //Coordinates;
                    String[] Coordinates = item[1].split(",");
                    setLat(Coordinates[0].trim());
                    setLong(Coordinates[1].trim());
                    break;
                case 3:
                    //Depth
                    setDepth(item[1].trim());
                    break;
                case 4:
                    //Magnitude
                    setMagnitude(item[1].trim());
                    break;

            }
            x++;
        }
    }



}
