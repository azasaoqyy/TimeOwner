package com.trigg.alarmclock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends ActionBarActivity {

    EditText ET_NAME, ET_USER_NAME, ET_USER_PASS;
    String name, user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ET_NAME = (EditText)findViewById(R.id.R_name);
        ET_USER_NAME = (EditText)findViewById(R.id.R_user_name);
        ET_USER_PASS = (EditText)findViewById(R.id.R_user_pass);

    }

    public void userReg(View view) {
        name = ET_NAME.getText().toString();
        user_name = ET_USER_NAME.getText().toString();
        user_pass = ET_USER_PASS.getText().toString();
        if (ET_NAME.getText().toString().equals(""))
        {
            Toast.makeText(Register.this, "name can't be null.", Toast.LENGTH_SHORT).show();
        }
        else if (ET_USER_NAME.getText().toString().equals(""))
        {
            Toast.makeText(Register.this, "User Name can't be null.", Toast.LENGTH_SHORT).show();
        }
        else if (ET_USER_PASS.getText().toString().equals(""))
        {
            Toast.makeText(Register.this, "Password can't be null.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String method = "register";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, name, user_name, user_pass);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
