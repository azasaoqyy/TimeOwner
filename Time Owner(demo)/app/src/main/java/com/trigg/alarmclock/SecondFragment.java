package com.trigg.alarmclock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

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

/**
 * Created by encore on 2015/7/20.
 *
 */
public class SecondFragment extends ListFragment {

    private ListView listView;
    private View view;
    public static String appname;
    public static int number;

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    public List<String> ar = new ArrayList<String>();
    public String[] arr;
    public static  ArrayList<String> checkedValue = new ArrayList<String>();
    private Context context;
    public AlarmDBHelper dbHelper;
    Button button;
    public boolean[] itemChecked;




    static SecondFragment newInstance(int num) {
        SecondFragment secondFragment = new SecondFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);

        secondFragment.setArguments(args);

        return secondFragment;
        }


@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.activity_all_apps, container, false);
        packageManager = getActivity().getPackageManager();
        //listView = (ListView)view.findViewById(R.id.list);
        button = (Button) view.findViewById(R.id.button1);

        dbHelper = new AlarmDBHelper(getActivity());

        dbHelper.createNumber(0,0);

        new LoadApplications().execute();




        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ApplicationInfo app = applist.get(position);
                itemChecked = new boolean[applist.size()];


                Log.d("pname", app.packageName);
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);

                cb.performClick();
                if (cb.isChecked()) {
                    Log.d("hi", app.packageName);
                    // add(app.packageName,position);
                    dbHelper.createPackage(app.packageName);

                    //ar.add(app.packageName.toString());
                    itemChecked[position] = true;
                } else if (!cb.isChecked()) {
                    Log.d("hi", app.packageName);
                    //delete(position);
                    dbHelper.deletePackage(position);
                    //ar.remove(app.packageName.toString());
                    itemChecked[position] = true;
                }
            }
        });*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences setting= getActivity().getApplicationContext().getSharedPreferences("loginInfo", 0);
                String job = setting.getString("job", "");
                //checkedValue = ar;
                //Intent startIntent = new Intent(AllAppsActivity.this, AlarmListActivity.class);
                //startActivity(startIntent);

                if(job.equals("n")){///self control fucking mode
                    number=dbHelper.getNumber(0);
                    dbHelper.updateNumber(0,number+1);

                    Log.d("number1", String.valueOf(number));
                    Intent intent = new Intent(getActivity(), AlarmDetailsActivity.class);
                    long id = -1;
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 0);
                }
                else if(job.equals("m")){///manager mode

                    number=dbHelper.getNumber(0);
                    dbHelper.updateNumber(0,number+1);
                    arr=dbHelper.getPackage(number);

                    for(int i=0;i<arr.length;i++)
                    {
                        String a = arr[i];
                        Package_work pw= new Package_work(getActivity());
                        pw.execute("save", a, Integer.toString(number));
                    }
                    Log.d("number1", String.valueOf(number));
                    Intent intent = new Intent(getActivity(), AlarmDetailsActivity.class);
                    long id = -1;
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 0);


                }


            }
        });


        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);
        itemChecked = new boolean[applist.size()];

        Log.d("pname", app.packageName);
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);


        cb.performClick();
        if (cb.isChecked()) {
            Log.d("hi", app.packageName);

            dbHelper = new AlarmDBHelper(getActivity());
            // add(app.packageName,position);
            dbHelper.createPackage(app.packageName,dbHelper.getNumber(0));
            //ar.add(app.packageName.toString());
            itemChecked[position] = true;
        } else if (!cb.isChecked()) {
            Log.d("hi", app.packageName);
            //delete(position);
            dbHelper.deletePackage(app.packageName,dbHelper.getNumber(0));
            //ar.remove(app.packageName.toString());
            itemChecked[position] = true;
        }
    }




    /*private void displayAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.about_title));
        builder.setMessage(getString(R.string.about_desc));


        builder.setPositiveButton("Know More", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com"));
                startActivity(browserIntent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        builder.show();
    }*/

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(getActivity(),
                    R.layout.activity_application_adapter, applist, itemChecked);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
//////////////////////////////////////////////////////////////////////////
    public class Package_work extends AsyncTask<String, String, String> {

        Context ctx;
        Package_work(Context ctx){
            this.ctx = ctx;
        }


        @Override
        protected String doInBackground(String... params) {
            String package_save_url = "http://120.126.16.70/kai/bjt/package_save.php";
            String job_set_url = "http://120.126.16.70/kai/bjt/job_set.php";
            String method = params[0];
            if (method.equals("save")){
                String name = params[1];
                String number = params[2];
                try {
                    URL url = new URL(package_save_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);

                    ///
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("name", "UTF-8") + "=" +URLEncoder.encode(name,"UTF-8") + "&" +
                            URLEncoder.encode("number","UTF-8") + "=" +URLEncoder.encode(number,"UTF-8");
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
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

            //super.onPostExecute(s);
            if (result.equals("Null")){



            }
            else if (result.equals("NotNull")){

            }
            else{

                //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            }
        }
    }

}
