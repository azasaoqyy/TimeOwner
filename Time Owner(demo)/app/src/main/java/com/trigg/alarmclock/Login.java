package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {

    EditText etUsername, etUserpass;
    String login_name, login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.user_name);
        etUserpass = (EditText) findViewById(R.id.user_pass);

        Button bToRegister = (Button) findViewById(R.id.bToRegister);
        bToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Q.Q", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        Button bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_name = etUsername.getText().toString();
                login_pass = etUserpass.getText().toString();
                String method = "login";
                if(login_name.equals("")){
                    Toast.makeText(Login.this, "請輸入帳號", Toast.LENGTH_LONG).show();
                }
                else if(login_pass.equals("")){
                    Toast.makeText(Login.this, "請輸入密碼", Toast.LENGTH_LONG).show();
                }
                else {
                    BackgroundTask backgroundTask = new BackgroundTask(Login.this);
                    backgroundTask.execute(method, login_name, login_pass);
                }
                //finish();
            }
        });


        /*Button disconnect = (Button) findViewById(R.id.disconnect);
        disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));

            }
        });*/
    }
}



