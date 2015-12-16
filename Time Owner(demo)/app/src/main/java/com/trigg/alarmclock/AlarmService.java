package com.trigg.alarmclock;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class AlarmService extends Service {

	public static String TAG = AlarmService.class.getSimpleName();
	//public ArrayList<String> name;
	//public String[] names;
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	private int i = 0;
	boolean john;
	private SQLiteDatabase DH;
	public static Packagethread thread;
	//public static List<String> names;
	private String[] names;
	@Override
	public void onCreate() {

		//thread.start();
		super.onCreate();
		thread = new Packagethread();
		thread.start();
		//names=null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/*name = (ArrayList<String>) dbHelper.getPackage();
		//name = AllAppsActivity.checkedValue;
		names = new String[name.size()];
		names = name.toArray(names);
		Log.d("onstart","start");*/
		/*Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtras(intent);
		getApplication().startActivity(alarmIntent);*/

		//name = AllAppsActivity.checkedValue;
		/*if(thread.isrunning==false) {

			thread = new Packagethread();
			thread.start();
		}
		else{

			thread.dh=0;
		}*/

		names = dbHelper.getPackage( dbHelper.getAlarm(intent.getExtras().getLong("id")).number);
		Log.d("numbersssss", String.valueOf(intent.getExtras().getLong("id")));
		Log.d("numbersssss", String.valueOf(dbHelper.getAlarm(intent.getExtras().getLong("id")).number));
		Log.d("numbersssss", names[0]);
		//names.addAll(name);

		for(int i=0;i<names.length;i++) {
			dbHelper.createPackage(names[i],100);
		}

		thread.dh=0;
		names=null;
		AlarmManagerHelper.setAlarms(this);


		//Log.d("ID", String.valueOf(id));


		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}



	public class Packagethread extends Thread {

		private boolean isrunning = true;
		public int dh=1;
		private ArrayList<String> name;
		private String[] names;
		@Override
		public void run( ) {



			while (isrunning) {
				if(dh==0) {
					getDH();
					dh=1;
				}
				//Log.d("d",namesss[0]);
				ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
				if(names==null) {

					//getDH();
				}
				else {
					for (i = 0; i < names.length; i++) {
						john = names[i].equals(taskInfo.get(0).topActivity.getPackageName());
						if (john == true) {
							i = names.length;
						}

					}
					if (john) {

						Intent intent = new Intent();
						Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
						alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						alarmIntent.putExtras(intent);
						getApplication().startActivity(alarmIntent);
						//Log.d(TAG, "thread2executed");

					}
				}
			}
		}
		public void getDH()
		{
			names = dbHelper.getPackage(100);

			//names = new String[name.size()];
			//names = name.toArray(names);

		}
		public void stopthread()

		{
			this.isrunning = false;
			//this.interrupt();
		}

	}
}
