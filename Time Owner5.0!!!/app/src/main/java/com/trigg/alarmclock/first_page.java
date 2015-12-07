package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class first_page extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        Button sign = (Button) findViewById(R.id.button1);
        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(first_page.this, Login.class));

            }
        });


        Button disconnect = (Button) findViewById(R.id.button2);
        disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(first_page.this, MainActivity.class));

            }
        });
    }


}
