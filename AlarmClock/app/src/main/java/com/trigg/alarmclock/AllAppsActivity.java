package com.trigg.alarmclock;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.internal.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class AllAppsActivity extends ListActivity  {

    public static String appname;

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    public ArrayList<String> ar = new ArrayList<String>();
    public String[] arr ;
    public static  ArrayList<String> checkedValue = new ArrayList<String>();
    public ArrayList <String> checkedValuethis;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    Button button;
    CheckBox checkBox;
    public boolean[] itemChecked;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        packageManager = getPackageManager();
        button = (Button) findViewById(R.id.button1);


        new LoadApplications().execute();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkedValue = ar;
                //Intent startIntent = new Intent(AllAppsActivity.this, AlarmListActivity.class);
                //startActivity(startIntent);
                Intent intent = new Intent(AllAppsActivity.this, AlarmDetailsActivity.class);
                long id = -1;
                intent.putExtra("id",id);
                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
              }
        else{

        }}
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.menu_about: {
                displayAboutDialog();

                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }

        return result;
    }


    private void displayAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }


    @Override

    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);
        itemChecked= new boolean[applist.size()];

        Log.d("pname", app.packageName);
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);


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
            itemChecked[position]=true;
        }

        /*ar.add(app.packageName);
        arr = new String[ar.size()];
        arr = ar.toArray(arr);
        super.onListItemClick(l, v, position, id);
        Intent startIntent =  new  Intent( this ,AlarmListActivity.class);
        startIntent.putExtra("packagename",app.packageName );
        startIntent.putExtra("packagelist", arr);
        appname = app.packageName;

       startActivity(startIntent);*/


		/*ApplicationInfo app = applist.get(position);
		try {
			Intent launchIntent = packageManager.getLaunchIntentForPackage(app.packageName);
			ComponentName className = launchIntent.getComponent();
			ComponentName componentName = new ComponentName(this,AllAppsActivity.class ); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
			packageManager.setComponentEnabledSetting(className, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


		} catch (ActivityNotFoundException e) {
			Toast.makeText(AllAppsActivity.this, e.getMessage(),
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(AllAppsActivity.this, e.getMessage(),
					Toast.LENGTH_LONG).show();
		}*/
    }


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

 public class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(AllAppsActivity.this,
                    R.layout.activity_application_adapter, applist,itemChecked);

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
            progress = ProgressDialog.show(AllAppsActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}