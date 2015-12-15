package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class first_page extends Activity {

    //public static File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        SharedPreferences setting = getSharedPreferences("loginInfo",0);
        String test = setting.getString("haveLogin", "0.0");
        if(test.equals("yes")){
            int user_id = setting.getInt("user_id",0);
            Intent it = new Intent();
            it.setClass(first_page.this, MainActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("user_id", user_id);

            it.putExtras(bundle);
            startActivity(it);
            finish();
            //startActivity(new Intent(first_page.this, MainActivity.class));
            //Toast.makeText(this, test, Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(this, test, Toast.LENGTH_LONG).show();
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
                Intent it = new Intent();
                it.setClass(first_page.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("user_id",0);
                it.putExtras(bundle);
                startActivity(it);
                finish();
            }
        });
    }


}
