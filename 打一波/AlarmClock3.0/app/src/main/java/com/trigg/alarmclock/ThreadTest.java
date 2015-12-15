package com.trigg.alarmclock;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadTest extends Activity implements Runnable {
    /** Called when the activity is first created. */

    public int Count_i;
    Thread thread;
    Button btn0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThreadTest.this.setContentView(R.layout.activity_thread_test);
               btn0 = (Button)findViewById(R.id.bT);

        btn0.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn0.setEnabled(false);
                EditText txt1 = (EditText)findViewById(R.id.etT);
                Count_i = Integer.parseInt(txt1.getText().toString());
                thread = new Thread(ThreadTest.this);
                thread.start();
            }
        });
    }

    private Handler handler =  new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int a = msg.what;

            TextView lab1 = (TextView)findViewById(R.id.tvT);
            Count_i--;

            String b = String.valueOf(Count_i);
            lab1.setText(String.valueOf(Count_i));
            Button btn0 = (Button)findViewById(R.id.bT);

            if (Count_i<=0)
            {
                lab1.setText("時間到");
                Toast.makeText(ThreadTest.this, "成功", Toast.LENGTH_SHORT).show();
                btn0.setEnabled(true);
            }
            else
            {
                Toast.makeText(ThreadTest.this, b, Toast.LENGTH_SHORT).show();
                thread = new Thread(ThreadTest.this);
                thread.start();
            }
        }
    };

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(1000);

            Message message=new Message();
            message.what=5;
            handler.sendMessage(message);//發送消息


            //handler.sendEmptyMessage(0);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}