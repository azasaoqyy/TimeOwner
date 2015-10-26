package com.example.computer;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public Button button7;
    public Button button8;
    public Button button9;
    public Button button0;
    public Button buttonadd;
    public Button buttoncut;
    public Button buttonmul;
    public Button buttonex;
    public Button buttonequ;
    public Button buttondel;
    public Button buttonpoint;
    public Button buttonMR;
    public Button buttonMS;
    public Button buttonMadd;
    public Button buttonMcut;
    public Button buttonMC;
    public RadioButton buttonM;
    public TextView textView;
    public double sum = 0.0;
    public double ans = 0.0;
    public int states;
    public int numstates=1;
    public double i=1;
    public double memory = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= (Button)findViewById(R.id.button);
        button2= (Button)findViewById(R.id.button2);
        button3= (Button)findViewById(R.id.button3);
        button4= (Button)findViewById(R.id.button4);
        button5= (Button)findViewById(R.id.button5);
        button6= (Button)findViewById(R.id.button6);
        button7= (Button)findViewById(R.id.button7);
        button8= (Button)findViewById(R.id.button8);
        button9= (Button)findViewById(R.id.button9);
        button0= (Button)findViewById(R.id.button16);
        buttonadd= (Button)findViewById(R.id.button10);
        buttoncut= (Button)findViewById(R.id.button11);
        buttonmul= (Button)findViewById(R.id.button12);
        buttonex= (Button)findViewById(R.id.button13);
        buttonequ= (Button)findViewById(R.id.button14);
        buttondel= (Button)findViewById(R.id.button15);
        buttonpoint= (Button)findViewById(R.id.button17);
       buttonMR= (Button)findViewById(R.id.button18);
         buttonMS= (Button)findViewById(R.id.button19);
         buttonMadd= (Button)findViewById(R.id.button20);
       buttonMcut= (Button)findViewById(R.id.button21);
        buttonMC= (Button)findViewById(R.id.button22);
        buttonM = (RadioButton)findViewById(R.id.radioButton);
        textView= (TextView)findViewById(R.id.textView);
        textView.setText(Double.toString(sum));
        button1.setOnClickListener(new Button.OnClickListener() {

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                switch (numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 1;
                        break;//中斷
                    case 2:
                        sum = sum + 1 * Math.pow(0.1, i);
                        i++;

                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button2.setOnClickListener(new Button.OnClickListener() {

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch (numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 2;
                        break;//中斷
                    case 2:
                        sum = sum + 2 * Math.pow(0.1, i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button3.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 3;
                        break;//中斷
                    case 2:
                        sum =sum + 3*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button4.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 4;
                        break;//中斷
                    case 2:
                        sum =sum + 4*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button5.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 5;
                        break;//中斷
                    case 2:
                        sum =sum + 5*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button6.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 6;
                        break;//中斷
                    case 2:
                        sum =sum + 6.0*Math.pow(0.10,i);
                        i++;
                        break;//中斷



                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button7.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 7;
                        break;//中斷
                    case 2:
                        sum =sum + 7*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button8.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 8;
                        break;//中斷
                    case 2:
                        sum =sum + 8*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button9.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 + 9;
                        break;//中斷
                    case 2:
                        sum =sum + 9*Math.pow(0.10,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });
        button0.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                switch(numstates)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        sum = sum * 10 ;
                        break;//中斷
                    case 2:
                        sum =sum + 0*Math.pow(0.1,i);
                        i++;
                        break;//中斷

                    default://預設的動作
                        break;
                }

                textView.setText(Double.toString(sum));

            }

        });

        buttonadd.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                switch(states)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        ans=ans+sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 2:
                        ans=ans-sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 3:
                        ans=ans*sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 4:
                        ans=ans/sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷

                    default://預設的動作
                        break;
                }
                // TODO Auto-generated method stub

                ans = sum;
                sum =0;
                states=1;
                numstates=1;
                i=1;


            }

        });
        buttoncut.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                switch(states)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        ans=ans+sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 2:
                        ans=ans-sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 3:
                        ans=ans*sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 4:
                        ans=ans/sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷

                    default://預設的動作
                        break;
                }
                // TODO Auto-generated method stub

                ans = sum;
                sum =0;
                states=2;
                numstates=1;
                i=1;

            }

        });
        buttonmul.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                switch(states)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        ans=ans+sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 2:
                        ans=ans-sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 3:
                        ans=ans*sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 4:
                        ans=ans/sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷

                    default://預設的動作
                        break;
                }
                // TODO Auto-generated method stub

                ans = sum;
                sum =0;
                states=3;
                numstates=1;
                i=1;

            }

        });
        buttonex.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                switch(states)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        ans=ans+sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 2:
                        ans=ans-sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 3:
                        ans=ans*sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 4:
                        ans=ans/sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷

                    default://預設的動作
                        break;
                }
                // TODO Auto-generated method stub

                ans = sum;
                sum =0;
                states=4;
                numstates=1;
                i=1;

            }

        });
        buttonequ.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                switch(states)   // 尋找a這個參數符合的條件
                {
                    case 1:
                        ans=ans+sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 2:
                        ans=ans-sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 3:
                        ans=ans*sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷
                    case 4:
                        ans=ans/sum;
                        sum=ans;
                        textView.setText(Double.toString(ans));
                        break;//中斷

                    default://預設的動作
                        break;
                }
                states = 0;
                numstates=1;
                i=1;
            }

        });
        buttondel.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                sum=0;
                ans=0;
                numstates=1;
                i=1;
                textView.setText("0");


            }

        });
        buttonpoint.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                numstates=2;



            }

        });

        buttonMR.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

              sum=memory;
                textView.setText(Double.toString(sum));
                buttonM.setChecked(true);


            }

        });
        buttonMadd.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

               memory=memory+sum;
                buttonM.setChecked(true);


            }

        });

        buttonMS.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                memory=sum;
                buttonM.setChecked(true);


            }

        });
        buttonMcut.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

               memory=memory-sum;
               buttonM.setChecked(true);


            }

        });
        buttonMC.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                memory=0;
                buttonM.setChecked(false);


            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
