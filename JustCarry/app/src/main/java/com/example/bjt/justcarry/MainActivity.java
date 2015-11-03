package com.example.bjt.justcarry;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Manifest;

public class MainActivity extends Activity {

    EditText etUsername, etUserpass;
    String login_name, login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText)findViewById(R.id.user_name);
        etUserpass = (EditText)findViewById(R.id.user_pass);

        Button bToRegister =(Button)findViewById(R.id.bToRegister);
        bToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //Toast.makeText(MainActivity.this, "Q.Q", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        Button bLogin = (Button)findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                login_name = etUsername.getText().toString();
                login_pass = etUserpass.getText().toString();
                String method = "login";
                BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
                backgroundTask.execute(method, login_name, login_pass);

            }
        });

    }

    /*public void userReg(View view){
        startActivity(new Intent(this, Register.class));
    }*/

    /*public void userLogin(View view) {

    }*/
}
