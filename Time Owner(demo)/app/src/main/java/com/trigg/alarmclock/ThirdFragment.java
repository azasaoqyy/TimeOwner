package com.trigg.alarmclock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by encore on 2015/7/20.
 */
public class ThirdFragment extends Fragment {

    private List<Setting> list = new ArrayList<Setting>();
    private View view;
    public AlarmDBHelper dbHelper;
    private AlarmModel alarmDetails;
    public int numbers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dbHelper = new AlarmDBHelper(getActivity());
        if(dbHelper.getNumber(0)==0)
        {
        dbHelper.createNumber(0,0);}
        view = inflater.inflate(R.layout.list,container,false);
        //listView = (ListView)view.findViewById(R.id.list);
        /*set();
        list();
        click();*/
        ListView listView = (ListView)view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();

                if (position == 0) {
                    dialog.setTitle("Title");
                    dialog.setMessage("Book1 Selected");
                } else if (position == 2) {
                    Intent startIntent = new Intent(getActivity(), Setting_Introduction.class);
                    startActivity(startIntent);
                } else if (position == 4) {
                    MySQL_Load alarm_load =new MySQL_Load(getActivity());
                    alarm_load.execute("alarm_load",Integer.toString(dbHelper.getNumber(0)));
                    MySQL_Load package_load =new MySQL_Load(getActivity());
                    package_load.execute("package_load",Integer.toString(dbHelper.getNumber(0)));
                }

                dialog.show();
            }

        });

        ArrayAdapter<Setting> adapter = new MyListAdapter();
        listView.setAdapter(adapter);

        //Resources resources = getResources();

        list.add(new Setting(getString(R.string.Change_Password), R.drawable.password, "\n" + getString(R.string.Change_PasswordD)));
        list.add(new Setting(getString(R.string.Remind), R.drawable.remind, "\n" + getString(R.string.RemindD)));
        list.add(new Setting(getString(R.string.Introduction), R.drawable.introduction, "\n" + getString(R.string.IntroductionD)));
        list.add(new Setting(getString(R.string.About_us), R.drawable.icon, ""));
        list.add(new Setting(getString(R.string.Sync), R.drawable.icon, ""));


        return view;
    }


    private class MyListAdapter extends ArrayAdapter<Setting> {

        public MyListAdapter() {
            super(getActivity(), R.layout.item, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.item, parent, false);
            }

            Setting currentSetting = list.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.image1);
            imageView.setImageResource(currentSetting.geticonID());

            TextView title = (TextView)itemView.findViewById(R.id.texttitle);
            title.setText(currentSetting.getTitle());

            TextView subtitle = (TextView)itemView.findViewById(R.id.textsubtitle);
            subtitle.setText(currentSetting.getSubtitle());

            return itemView;
        }
    }

    public class MySQL_Load extends AsyncTask<String, String, String> {

        int set = 0;

        Context ctx;
        MySQL_Load(Context ctx){
            this.ctx = ctx;
        }


        @Override
        protected String doInBackground(String... params) {
            String alarm_load_url = "http://120.126.16.70/kai/bjt/alarm_load.php";
            String package_load_url = "http://120.126.16.70/kai/bjt/package_load.php";
            String method = params[0];
            if (method.equals("alarm_load")){
                String number = params[1];
                set =1;

                try {
                    URL url = new URL(alarm_load_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);

                    ///
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data =URLEncoder.encode("number","UTF-8") + "=" +URLEncoder.encode(number,"UTF-8");

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
            else if (method.equals("package_load")){
                String number = params[1];
                set =2;

                try {
                    URL url = new URL(package_load_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);

                    ///
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("number","UTF-8") + "=" +URLEncoder.encode(number,"UTF-8");

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
            if (set == 2){
                dbHelper = new AlarmDBHelper(getActivity());
                //super.onPostExecute(s);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i< jsonArray.length();i++) {
                        JSONObject test = jsonArray.getJSONObject(i);
                        String name = test.getString("name");
                        String number = test.getString("number");
                        dbHelper.createPackage(name,Integer.parseInt(number));
                        Toast.makeText(ctx, "已同步", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
            if (set == 1){
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i< jsonArray.length();i++) {
                        JSONObject test = jsonArray.getJSONObject(i);
                        String name = test.getString("name");
                        String hour = test.getString("hour");
                        String minute = test.getString("minute");
                        String hour_end = test.getString("hour_end");
                        String minute_end = test.getString("minute_end");
                        String days = test.getString("days");
                        String weekly = test.getString("weekly");
                        String tone = test.getString("tone");
                        String enabled = test.getString("enabled");
                        String number = test.getString("number");
                        alarmDetails = new AlarmModel();
                        alarmDetails.timeMinute = Integer.parseInt(minute);
                        alarmDetails.timeHour = Integer.parseInt(hour);

                        alarmDetails.timeMinuteend = Integer.parseInt(minute_end);
                        alarmDetails.timeHourend = Integer.parseInt(hour_end);

                        alarmDetails.name = name;
                        alarmDetails.repeatWeekly = Boolean.parseBoolean(weekly);
                        alarmDetails.setRepeatingDay(AlarmModel.SUNDAY,true );
                        alarmDetails.setRepeatingDay(AlarmModel.MONDAY,true );
                        alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, true);
                        alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY,true );
                        alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, true);
                        alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, true);
                        alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, true);
                        alarmDetails.isEnabled = true;
                        alarmDetails.number=dbHelper.getNumber(0);
                        dbHelper = new AlarmDBHelper(getActivity());
                        dbHelper.createAlarm(alarmDetails);
                        AlarmManagerHelper.setAlarms(getActivity());
                        AlarmManagerHelperEnd.setAlarms(getActivity());
                        numbers = dbHelper.getNumber(0);
                        numbers=numbers+1;
                        dbHelper.updateNumber(0,numbers);
                        //setResult(RESULT_OK);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

