package com.trigg.alarmclock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by user on 2015/12/1.
 */
public class AlarmServiceEnd extends Service {
    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        AlarmService.thread.stopthread();
        AlarmService.thread.interrupt();

        AlarmManagerHelperEnd.setAlarms(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
