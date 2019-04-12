package me.chaig.mpd.mpdearthquakes.listeners;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.chaig.mpd.mpdearthquakes.R;
import me.chaig.mpd.mpdearthquakes.RSSFeed;
import me.chaig.mpd.mpdearthquakes.models.Earthquake;
import me.chaig.mpd.mpdearthquakes.models.Mapview;
import me.chaig.mpd.mpdearthquakes.models.ViewEditor;

import static android.app.DatePickerDialog.*;

public class SortSelector implements AdapterView.OnItemSelectedListener {

    /*
        Name: Christopher Haig
        USERNAME: CHAIG200
        Matric Number: S1709869
     */

    public Activity activity;
    private RSSFeed feed;

    /**
     *
     * @param activity -  The activity that is being affected.
     * @param feed -  The RssFeed that stores the earthquakes
     */
    public SortSelector(Activity activity,RSSFeed feed) {
        this.activity = activity;
        this.feed = feed;
    }

    /**
     *
     * @param activity -  The activity that is being affected.
     */
    public SortSelector(Activity activity) {
        this.activity = activity;
    }


    /*
        This listener generates all the inputs for the selection that the user made
        for eg if the user selected date it would generate 2 edit texts for the dates to be inputed
        and so forth.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        LinearLayout layoutH1 = activity.findViewById(R.id.inputsH);
        LinearLayout layoutH2 = activity.findViewById(R.id.inputsH2);
        LinearLayout layoutH3 = activity.findViewById(R.id.Search);

        if(adapterView.getSelectedItemPosition() == 0){
            clearViews();
            new Mapview().displayAll(activity,feed);
        }else{
            Button search = new Button(activity);
            search.setText("Search for earthquakes");
            search = (Button) alterViewWeight(search,100);
            String type = adapterView.getSelectedItem().toString();
            switch(type){

                case "Date":
                    clearViews();
                    /*
                        Start date label
                     */
                    TextView start = new TextView(activity);
                    start = new ViewEditor().ViewToLabel(start,"Start Date");
                    start = (TextView) alterViewWeight(start,50);
                    layoutH1.addView(start,0);

                    /*
                     Start date input
                     */
                    EditText startDate = new EditText(activity);
                    startDate = prepareEditText(startDate,false);
                    startDate.setOnClickListener(new dateDialogOnClickListener(activity,startDate));
                    layoutH2.addView(startDate,0);


                    /*
                       End date label
                     */
                    TextView end = new TextView(activity);
                    end = new ViewEditor().ViewToLabel(end,"End Date");
                    end = (TextView) alterViewWeight(end,50);
                    layoutH1.addView(end,1);

                    /*
                        End date input
                     */
                    EditText endDate = new EditText(activity);
                    endDate = prepareEditText(endDate,false);
                    endDate.setOnClickListener(new dateDialogOnClickListener(activity,endDate));
                    layoutH2.addView(endDate,1);


                    search.setOnClickListener(new SearchQueryListener(feed,"Date",new Object[]{startDate,endDate}));

                    layoutH3.addView(search,0);


                    break;

                case "Time":
                    clearViews();

                    TextView Date = new TextView(activity);
                    Date = new ViewEditor().ViewToLabel(Date,"Date");
                    Date = (TextView) alterViewWeight(Date,50);
                    layoutH1.addView(Date,0);

                    EditText date = new EditText(activity);
                    date = prepareEditText(date,false);
                    date.setOnClickListener(new dateDialogOnClickListener(activity,date));

                    layoutH2.addView(date,0);

                       /*
                        Start time label
                     */
                    TextView StartTime = new TextView(activity);
                    StartTime = new ViewEditor().ViewToLabel(StartTime,"Start Time");
                    StartTime = (TextView) alterViewWeight(StartTime,50);
                    layoutH1.addView(StartTime,1);

                    /*
                     Start time input
                     */

                    EditText startTime = new EditText(activity);
                    startTime = prepareEditText(startTime,false);
                    startTime.setOnClickListener(new timeDialogOnClickLIstener(activity,startTime));
                    layoutH2.addView(startTime,1);


                    /*
                       End time label
                     */
                    TextView EndTime = new TextView(activity);
                    EndTime = new ViewEditor().ViewToLabel(EndTime,"End Time");
                    EndTime = (TextView) alterViewWeight(EndTime,50);
                    layoutH1.addView(EndTime,2);

                    /*
                        End time input
                     */
                    EditText endTime = new EditText(activity);
                    endTime = prepareEditText(endTime,false);
                    endTime.setOnClickListener(new timeDialogOnClickLIstener(activity,endTime));
                    layoutH2.addView(endTime,2);
                    search.setOnClickListener(new SearchQueryListener(feed,"Time",new Object[]{date,startTime,endTime}));

                    layoutH3.addView(search,0);

                    break;

                case "Magnitude":
                    clearViews();

                      /*
                        Lowest Magnitude label
                     */
                    TextView lowest = new TextView(activity);
                    lowest = new ViewEditor().ViewToLabel(lowest,"Lowest Magnitude");
                    lowest = (TextView) alterViewWeight(lowest,50);
                    layoutH1.addView(lowest,0);

                    /*
                     Lowest Magnitude input
                     */

                    EditText lowestMagnitude = new EditText(activity);
                    lowestMagnitude = prepareEditText(lowestMagnitude,true);
                    lowestMagnitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lowestMagnitude.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
                    layoutH2.addView(lowestMagnitude,0);


                    /*
                       Highest Magnitude label
                     */
                    TextView Highest = new TextView(activity);
                    Highest = new ViewEditor().ViewToLabel(Highest,"Highest Magnitude");
                    Highest = (TextView) alterViewWeight(Highest,50);
                    layoutH1.addView(Highest,1);

                    /*
                        Highest Magnitude input
                     */
                    EditText highestMagnitude = new EditText(activity);
                    highestMagnitude = prepareEditText(highestMagnitude,true);
                    highestMagnitude.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    highestMagnitude.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
                    layoutH2.addView(highestMagnitude,1);

                    search.setOnClickListener(new SearchQueryListener(feed,"Magnitude",new Object[]{lowestMagnitude,highestMagnitude}));

                    layoutH3.addView(search,0);
                    break;

                case "Depth":
                    clearViews();


                     /*
                     Shallowest Depth label
                     */
                    TextView shallowest = new TextView(activity);
                    shallowest = new ViewEditor().ViewToLabel(shallowest,"Shallowest Depth");
                    shallowest = (TextView) alterViewWeight(shallowest,50);
                    layoutH1.addView(shallowest,0);

                    /*
                     Shallowest Depth input
                     */

                    EditText shallow = new EditText(activity);
                    shallow = prepareEditText(shallow,true);
                    shallow.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    layoutH2.addView(shallow,0);


                    /*
                    Deepest Depth label
                     */
                    TextView Deepest = new TextView(activity);
                    Deepest = new ViewEditor().ViewToLabel(Deepest,"Deepest Depth");
                    Deepest = (TextView) alterViewWeight(Deepest,50);
                    layoutH1.addView(Deepest,1);

                    /*
                     Deepest Depth input
                     */
                    EditText Deep = new EditText(activity);
                    Deep = prepareEditText(Deep,true);
                    Deep.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    layoutH2.addView(Deep,1);

                    search.setOnClickListener(new SearchQueryListener(feed,"Depth",new View[]{shallow,Deep}));
                    layoutH3.addView(search,0);
                    break;

                case "Location":
                    clearViews();
                    /*
                        Location label
                     */
                    TextView location = new TextView(activity);
                    location = new ViewEditor().ViewToLabel(location,"Location");
                    location = (TextView) alterViewWeight(location,100);
                    layoutH1.addView(location,0);


                    /*
                     Location picker
                     */
                    Spinner locationPicker = new Spinner(activity);


                    ArrayList<String> locations = new ArrayList<>();
                    for(Earthquake e : feed.getEarthquakes()){
                        if(!locations.contains(e.getLocation())) locations.add(e.getLocation());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, locations.toArray(new String[locations.size()]));
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    locationPicker.setAdapter(adapter);
                    locationPicker.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    locationPicker.setBackgroundColor(Color.parseColor("#0081BD"));
                    locationPicker = (Spinner) alterViewWeight(locationPicker,100);
                    locationPicker.setMinimumHeight(150);
                    layoutH2.addView(locationPicker,0);

                    search.setOnClickListener(new SearchQueryListener(feed,"Location",new Object[]{locationPicker}));
                    layoutH3.addView(search,0);

                    break;

               case "Most Northern":
                    clearViews();

                    /*
                     Date label
                     */
                    TextView MNStart = new TextView(activity);
                    MNStart = new ViewEditor().ViewToLabel(MNStart,"Date");
                    MNStart = (TextView) alterViewWeight(MNStart,50);
                    layoutH1.addView(MNStart,0);

                    /*
                     Date input
                     */

                    EditText MNstartDate = new EditText(activity);
                    MNstartDate = prepareEditText(MNstartDate,false);
                    MNstartDate.setOnClickListener(new dateDialogOnClickListener(activity,MNstartDate));

                    layoutH2.addView(MNstartDate,0);

                    search.setOnClickListener(new SearchQueryListener(feed,"Most Northern",new Object[]{MNstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Most Eastern":
                    clearViews();

                    /*
                     Date label
                     */
                    TextView MEStart = new TextView(activity);
                    MEStart = new ViewEditor().ViewToLabel(MEStart,"Date");
                    MEStart = (TextView) alterViewWeight(MEStart,50);
                    layoutH1.addView(MEStart,0);

                    /*
                     Date input
                     */

                    EditText MEstartDate = new EditText(activity);
                    MEstartDate = prepareEditText(MEstartDate,false);
                    MEstartDate.setOnClickListener(new dateDialogOnClickListener(activity,MEstartDate));
                    layoutH2.addView(MEstartDate,0);


                    search.setOnClickListener(new SearchQueryListener(feed,"Most Eastern",new Object[]{MEstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Most Southern":
                    clearViews();

                     /*
                     Date label
                     */

                    TextView MSStart = new TextView(activity);
                    MSStart = new ViewEditor().ViewToLabel(MSStart,"Date");
                    MSStart = (TextView) alterViewWeight(MSStart,50);
                    layoutH1.addView(MSStart,0);

                    /*
                     Date input
                     */

                    EditText MSstartDate = new EditText(activity);
                    MSstartDate = prepareEditText(MSstartDate,false);
                    MSstartDate.setOnClickListener(new dateDialogOnClickListener(activity,MSstartDate));
                    layoutH2.addView(MSstartDate,0);


                    search.setOnClickListener(new SearchQueryListener(feed,"Most Southern",new Object[]{MSstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Most Western":
                    clearViews();

                    /*
                     Date label
                     */
                    TextView MWStart = new TextView(activity);
                    MWStart = new ViewEditor().ViewToLabel(MWStart,"Date");
                    MWStart = (TextView) alterViewWeight(MWStart,50);
                    layoutH1.addView(MWStart,0);

                    /*
                     Date input
                     */

                    EditText MWstartDate = new EditText(activity);
                    MWstartDate = prepareEditText(MWstartDate,false);
                    MWstartDate.setOnClickListener(new dateDialogOnClickListener(activity,MWstartDate));
                    layoutH2.addView(MWstartDate,0);


                    search.setOnClickListener(new SearchQueryListener(feed,"Most Western",new Object[]{MWstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Highest Magnitude":
                    clearViews();

                     /*
                     Date label
                     */
                    TextView HMStart = new TextView(activity);
                    HMStart = new ViewEditor().ViewToLabel(HMStart,"Date");
                    HMStart = (TextView) alterViewWeight(HMStart,50);
                    layoutH1.addView(HMStart,0);

                    /*
                     Date input
                     */

                    EditText HMstartDate = new EditText(activity);
                    HMstartDate = prepareEditText(HMstartDate,false);
                    HMstartDate.setOnClickListener(new dateDialogOnClickListener(activity,HMstartDate));
                    layoutH2.addView(HMstartDate,0);

                    search.setOnClickListener(new SearchQueryListener(feed,"Highest Magnitude",new Object[]{HMstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Deepest Earthquake":
                    clearViews();

                     /*
                     Date label
                     */
                    TextView DEStart = new TextView(activity);
                    DEStart = new ViewEditor().ViewToLabel(DEStart,"Date");
                    DEStart = (TextView) alterViewWeight(DEStart,50);
                    layoutH1.addView(DEStart,0);

                    /*
                     Date input
                     */

                    EditText DEstartDate = new EditText(activity);
                    DEstartDate = prepareEditText(DEstartDate,false);
                    DEstartDate.setOnClickListener(new dateDialogOnClickListener(activity,DEstartDate));
                    layoutH2.addView(DEstartDate,0);

                    search.setOnClickListener(new SearchQueryListener(feed,"Deepest Earthquake",new Object[]{DEstartDate}));
                    layoutH3.addView(search,0);
                    break;

                case "Shallowest Earthquake":
                    clearViews();
                     /*
                     Date label
                     */
                    TextView SEStart = new TextView(activity);
                    SEStart = new ViewEditor().ViewToLabel(SEStart,"Date");
                    SEStart = (TextView) alterViewWeight(SEStart,50);
                    layoutH1.addView(SEStart,0);

                    /*
                     Date input
                     */

                    EditText SEstartDate = new EditText(activity);
                    SEstartDate = prepareEditText(SEstartDate,false);
                    SEstartDate.setOnClickListener(new dateDialogOnClickListener(activity,SEstartDate));
                    layoutH2.addView(SEstartDate,0);

                    search.setOnClickListener(new SearchQueryListener(feed,"Shallowest Earthquake",new Object[]{SEstartDate}));
                    layoutH3.addView(search,0);
                    break;

            }


        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     *
     * @param v - The view being altered.
     * @param weight - The weight you want to apply to said view.
     * @return - Altered view
     */
    private View alterViewWeight(View v, int weight){
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.weight = weight;

        v.setLayoutParams(p);
        return v;
    }

    /*
    This method clears all of the views that would contain inputs.
     */
    private void clearViews(){
        LinearLayout layoutH1 = activity.findViewById(R.id.inputsH);
        LinearLayout layoutH2 = activity.findViewById(R.id.inputsH2);
        LinearLayout layoutH3 = activity.findViewById(R.id.Search);
        layoutH1.removeAllViews();
        layoutH2.removeAllViews();
        layoutH3.removeAllViews();
    }

    /**
     *
     * @param editText - The EditText that is being altered.
     * @return
     */
    /*
       This alters all of the properties of the edit text such as text color etc.
       This method was created to minimise the duplication of the code.
     */
    public EditText prepareEditText(EditText editText, boolean editable){
        editText.setBackgroundColor(Color.parseColor("#ffffff"));
        if(editable == false){
            editText.setFocusableInTouchMode(false);
        }
        editText.setBackground(getBorder());
        editText.setTextColor(Color.parseColor("#ffffff"));
        editText = (EditText) alterViewWeight(editText,50);
        editText.setMaxLines(1);
        return editText;
    }

    /*
      This generates a border that can be applied to numerous different views.
     */
    private ShapeDrawable getBorder(){
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.parseColor("#0081BD"));
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(3);
        return shape;
    }


    /**
     *
     * @param date -  The EditText you would like to open a date picker with when clicked.
     * @return
     */
    /*
        This method generates a date picker when the EditText that was passed in is clicked.
        After a date has been selected it will set the text on that EditText to the date selected.
     */
    protected DatePickerDialog setDateField(final EditText date) {

        final EditText editText = date;
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat sd = new SimpleDateFormat("dd MMM yyyy");
                    final Date startDate = newDate.getTime();
                    String fdate = sd.format(startDate);

                editText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        return mDatePickerDialog;

    }

    /**
     *
     * @param time -  The EditText you would like to open a time picker with when clicked.
     * @return
     */
    /*
        This method generates a date picker when the EditText that was passed in is clicked.
        After a time has been selected it will set the text on that EditText to the time selected.
     */

    protected TimePickerDialog setTimeField(final EditText time) {

        final EditText editText = time;
        Calendar newCalendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(i < 10){
                    time.setText("0"+i+":" + i1);
                }else{
                    time.setText(i+":" + i1);
                }
            }

        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        return  timePickerDialog;

    }
}
