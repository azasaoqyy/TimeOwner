package com.trigg.alarmclock;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    ActionBar actionBar;
    ViewPager viewpager;
    FragmentPagerAdapter ft;
    int user_id;
    String mac_id="";
    int job_set = 0;


    ///接收從Background來的資料



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ///接收從Background來的資料
        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("user_id") != 0){
            user_id = bundle.getInt("user_id");
            SharedPreferences setting= getSharedPreferences("loginInfo",0);
            SharedPreferences.Editor editor = setting.edit();
            editor.putString("haveLogin", "yes");
            editor.putInt("user_id", user_id);
            editor.commit();
           // Toast.makeText(this,user_id,Toast.LENGTH_LONG).show();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mac_id = Build.SERIAL;
                //Toast.makeText(this,serial,Toast.LENGTH_LONG).show();
            }
            JsonTask jsonTask = new JsonTask(this);
            jsonTask.execute("job_test",Integer.toString(user_id),mac_id);
        }

        /*if (job_set == 1 ){
            //JsonTask jsonTask = new JsonTask(this);
            //jsonTask.execute("job_set",Integer.toString(user_id),mac_id);
            Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
        }
        if (job_set == 2){
            Toast.makeText(this,"2",Toast.LENGTH_SHORT).show();
        }*/

        /////試著判斷登入與否來決定是要顯示登入還是登出
        /*SharedPreferences logOrNot= getSharedPreferences("loginInfo",0);
        String log = logOrNot.getString("haveLogin", "");
        if (log.equals("login_success")){
            MenuItem i =(MenuItem)findViewById(R.id.logout);
            i.setTitle("123");
        }
        else{
            MenuItem i =(MenuItem)findViewById(R.id.logout);
            i.setTitle("456");
        }*/




        ft = new FragmentPageAdapter(getSupportFragmentManager());

        viewpager = (ViewPager) findViewById(R.id.pager);

        viewpager.setAdapter(ft);



        actionBar = getSupportActionBar();


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);


        actionBar.addTab(actionBar.newTab().setText(" schedule").setIcon(R.drawable.schedule).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(" new alarm").setIcon(R.drawable.add_alarm).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(" setting").setIcon(R.drawable.setting).setTabListener(this));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SharedPreferences logOrNot= getSharedPreferences("loginInfo",0);
        String log = logOrNot.getString("haveLogin", "");
        if (log.equals("yes")){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        else{
            getMenuInflater().inflate(R.menu.menu_if_no_login, menu);
            return true;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        SharedPreferences logOrNot= getSharedPreferences("loginInfo",0);
        String log = logOrNot.getString("haveLogin", "");
        if (log.equals("yes")){
            switch (item.getItemId()){


                case R.id.logout:

                    SharedPreferences setting= getSharedPreferences("loginInfo",0);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),first_page.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        }
        else{
            switch (item.getItemId()){


                case R.id.logout:
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


public class JsonTask extends AsyncTask<String, String, String> {

    Context ctx;
    JsonTask(Context ctx){
        this.ctx = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String job_test_url = "http://192.168.31.86/webapp/job_test.php";
        String job_set_url = "http://192.168.31.86/webapp/job_set.php";
        String method = params[0];
        if (method.equals("job_test")){
            String user_id = params[1];
            String mac_id = params[2];
            try {
                URL url = new URL(job_test_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);

                ///
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" +URLEncoder.encode(user_id,"UTF-8") + "&" +
                        URLEncoder.encode("mac_id","UTF-8") + "=" +URLEncoder.encode(mac_id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                ///

                ///
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response ="";
                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    response = response + line;
                }
                bufferedReader.close();
                inputStream.close();
                ///

                //InputStream IS = httpURLConnection.getInputStream();
                //IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("job_set")){
            String user_id = params[1];
            String mac_id = params[2];
            String job = params[3];
            try {
                URL url = new URL(job_set_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);

                ///
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" +URLEncoder.encode(user_id,"UTF-8") + "&" +
                        URLEncoder.encode("job", "UTF-8") + "=" +URLEncoder.encode(job,"UTF-8") + "&" +
                        URLEncoder.encode("mac_id","UTF-8") + "=" +URLEncoder.encode(mac_id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                ///

                ///
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response ="";
                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    response = response + line;
                }
                bufferedReader.close();
                inputStream.close();
                ///

                //InputStream IS = httpURLConnection.getInputStream();
                //IS.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {


        //super.onPostExecute(s);
        if (result.equals("Null")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("請選擇此裝置的身分：");
            builder.setItems(R.array.job_sel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case 0://選擇管理者
                            String job_m = "m";
                            JsonTask jsonTask0 = new JsonTask(ctx);
                            jsonTask0.execute("job_set",Integer.toString(user_id), mac_id, job_m);
                            //Toast.makeText(ctx,Integer.toString(user_id),Toast.LENGTH_LONG).show();
                            break;
                        case 1://選擇被管理者
                            String job_s = "s";
                            JsonTask jsonTask1 = new JsonTask(ctx);
                            jsonTask1.execute("job_set",Integer.toString(user_id), mac_id, job_s);
                            break;
                    }
                    // The 'which' argument contains the index position
                    // of the selected item
                    //Toast.makeText(ctx,Integer.toString(which),Toast.LENGTH_LONG).show();
                }
            });

            builder.show();
        }
        else if (result.equals("NotNull")){

        }
        else{

            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }
    }
}


}
