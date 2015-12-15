package com.trigg.alarmclock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends ActionBarActivity {

    EditText etName,etUsername,etUserpass;
    String name, user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText)findViewById(R.id.name);
        etUsername = (EditText)findViewById(R.id.new_user_name);
        etUserpass = (EditText)findViewById(R.id.new_user_pass);
        Button bRegister = (Button)findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                name = etName.getText().toString();
                user_name = etUsername.getText().toString();
                user_pass = etUserpass.getText().toString();
                String method = "register";
                if(user_name.equals("")){
                    Toast.makeText(Register.this,"Username is null.",Toast.LENGTH_LONG).show();
                }
                else {
                    BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                    backgroundTask.execute(method, name, user_name, user_pass);
                }
                //finish();
            }
        });

    }
}
