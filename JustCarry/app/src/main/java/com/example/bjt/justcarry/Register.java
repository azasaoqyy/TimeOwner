package com.example.bjt.justcarry;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by BJT on 2015/10/26.
 */
public class Register extends Activity {

    EditText etName,etUsername,etUserpass;
    String name, user_name, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
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
                BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                backgroundTask.execute(method, name, user_name, user_pass);
                finish();
            }
        });

    }

    /*public void userReg(View view){
        name = etName.getText().toString();
        user_name = etUsername.getText().toString();
        user_pass = etUserpass.getText().toString();
        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, name, user_name, user_pass);
        finish();
    }*/

}
