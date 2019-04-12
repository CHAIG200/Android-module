package me.chaig.mpd.mpdearthquakes.models;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class ViewEditor {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    /**
     *
     * @param v - The TextView that is to be edited.
     * @param text - The text that the TextView will display.
     * @return
     */
    public TextView ViewToLabel(View v, String text){
        TextView textView = (TextView) v;
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setBackgroundColor(Color.parseColor("#0081BD"));
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        return  textView;
    }

}
