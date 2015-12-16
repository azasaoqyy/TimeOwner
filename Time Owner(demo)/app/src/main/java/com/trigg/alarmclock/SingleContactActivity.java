package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class SingleContactActivity extends Activity {

    // JSON node keys
    private static final String TAG_TITLE = "id";
    private static final String TAG_LOCATION = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String title = in.getStringExtra(TAG_TITLE);
        String location = in.getStringExtra(TAG_LOCATION);


        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.title_label);
        TextView lblLocation = (TextView) findViewById(R.id.location_label);


        lblTitle.setText(title);
        lblLocation.setText(location);

    }

}
