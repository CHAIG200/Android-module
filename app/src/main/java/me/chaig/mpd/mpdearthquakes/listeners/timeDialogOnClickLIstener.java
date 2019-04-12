package me.chaig.mpd.mpdearthquakes.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class timeDialogOnClickLIstener implements View.OnClickListener {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    public EditText editText;
    public Activity activity;

    /**
     *
     * @param activity -  The activity of the view that is getting edited
     * @param editText -  The editText that is getting changed into a date picker.
     */
    public timeDialogOnClickLIstener(Activity activity, EditText editText){
        this.activity = activity;
        this.editText = editText;
    }

    /*
       This changes the text of the editText that was selected to the time that was selected.
    */
    @Override
    public void onClick(View view) {
        SortSelector sortSelector = new SortSelector(activity);
        sortSelector.setTimeField(editText).show();
    }
}
