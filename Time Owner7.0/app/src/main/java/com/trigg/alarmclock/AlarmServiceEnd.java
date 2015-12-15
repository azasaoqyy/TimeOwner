package com.trigg.alarmclock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by user on 2015/12/1.
 */
public class AlarmServiceEnd extends Service {
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //AlarmService.thread.stopthread();
        //AlarmService.thread.interrupt();
        String[] name = dbHelper.getPackage( dbHelper.getAlarm(intent.getExtras().getLong("id")).number);
        for(int i=0;i<name.length;i++) {
            dbHelper.deletePackage(name[i],100);
        }
        AlarmService.thread.dh=0;
        name=null;
        AlarmManagerHelperEnd.setAlarms(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
