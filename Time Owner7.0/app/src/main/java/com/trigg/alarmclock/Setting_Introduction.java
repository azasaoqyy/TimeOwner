package com.trigg.alarmclock;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Setting_Introduction extends Activity {

    ViewPager viewPager;
    Setting_Introduction_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__introduction);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new Setting_Introduction_Adapter(this);
        viewPager.setAdapter(adapter);
    }


}
