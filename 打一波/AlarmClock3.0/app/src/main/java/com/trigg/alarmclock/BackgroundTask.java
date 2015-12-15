package com.trigg.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Taco on 2015/8/28.
 */
public class BackgroundTask extends AsyncTask<String, Void, String> {

    //SharedPreferences setting = getSharedPreferences("LoginInfo",0);
    public static File file;
    int a;

    AlertDialog alertDialog;

    /////
    Context ctx;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }
    /////

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information...");
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://192.168.31.86/webapp/register.php";
        String login_url = "http://192.168.31.86/webapp/login.php";
        String method = params[0];
        if (method.equals("register")){
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);

                ///
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("user","UTF-8") + "=" +URLEncoder.encode(name,"UTF-8") + "&" +
                        URLEncoder.encode("user_name","UTF-8") + "=" +URLEncoder.encode(user_name,"UTF-8") + "&" +
                        URLEncoder.encode("user_pass","UTF-8") + "=" +URLEncoder.encode(user_pass,"UTF-8");
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
        else if (method.equals("login")){
            String login_name = params[1];
            String login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")
                        +"&"+URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                //JSONArray a;
                String response ="";
                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    response = response + line;
                }
                bufferedReader.close();
                inputStream.close();
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){

        /*if(result.equals("login_success")) {
            //Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
            //ctx.startActivity(new Intent(ctx, MainActivity.class));
            //Toast.makeText(ctx, "Login Success!!", Toast.LENGTH_LONG).show();
            Intent it = new Intent();
            it.setClass(ctx,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("RS",result);
            it.putExtras(bundle);
            ctx.startActivity(it);
        }*/
        if (result.equals("register_success")) {
            //alertDialog.setMessage("Register Success!!");
            //alertDialog.show();
            Toast.makeText(ctx, "Register Success!!", Toast.LENGTH_LONG).show();
        }
        else if (result.equals("username_duplicate")) {
            //alertDialog.setMessage("Register Success!!");
            //alertDialog.show();
            Toast.makeText(ctx, "Username Duplicate!!", Toast.LENGTH_LONG).show();
        }
        else {
            a=0;
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject test =jsonArray.getJSONObject(0);
                String testS = test.getString("user_id");
                int user_id = Integer.parseInt(testS);
                a=1;
                //Toast.makeText(ctx, testS, Toast.LENGTH_LONG).show();
                Intent it = new Intent();
                it.setClass(ctx,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", user_id);
                it.putExtras(bundle);
                ctx.startActivity(it);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (a == 0){
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            }

            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
    }
}
