package com.trigg.alarmclock;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;



public class AlarmService extends Service {

	public static String TAG = AlarmService.class.getSimpleName();
	public String name;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		/*Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtras(intent);
		getApplication().startActivity(alarmIntent);*/
		name=AllAppsActivity.appname;
        thread.start();
		
		AlarmManagerHelper.setAlarms(this);
		
		return super.onStartCommand(intent, flags, startId);
	}

	public Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			while ( true ) {

				ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
				List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

				// Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getPackageName());
				//String name= taskInfo.get(0).topActivity.getPackageName();

				// String name[]={"mong.moptt"};
				// Log.d("name","eeeeee"+name+"eeeeeeee");
				/*for(int i=0; i<namesss.length;i++)
				{
					john = namesss[i].equals(taskInfo.get(0).topActivity.getPackageName());
					if(john==true){i=100;}

				}*/
				if(name.equals(taskInfo.get(0).topActivity.getPackageName()))
				{

					Intent intent = new Intent();
					Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
					alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					alarmIntent.putExtras(intent);
					getApplication().startActivity(alarmIntent);
					//Log.d(TAG, "thread2executed");

				}

			}
		}
	});
}