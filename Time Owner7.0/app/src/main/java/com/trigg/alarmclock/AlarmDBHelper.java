package com.trigg.alarmclock;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.trigg.alarmclock.AlarmContract.Alarm;

public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";

    private static final String SQL_CREATE_ALARM = "CREATE TABLE " + Alarm.TABLE_NAME + " (" +
            Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Alarm.COLUMN_NAME_ALARM_NAME + " TEXT," +
            Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER," +
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " INTEGER," +

            Alarm.COLUMN_NAME_ALARM_TIME_HOUR_END + " INTEGER," +
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE_END + " INTEGER," +

            Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS + " TEXT," +
            Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY + " BOOLEAN," +
            Alarm.COLUMN_NAME_ALARM_TONE + " TEXT," +
            Alarm.COLUMN_NAME_ALARM_ENABLED + " BOOLEAN," +
            Alarm.COLUMN_NAME_ALARM_NUMBER+" INTEGER"+
            " )";
    //package
    private static final String SQL_CREATE_PACKAGE =  "CREATE TABLE " + Alarm.TABLE_NAME_P + " (" +
            Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Alarm.COLUMN_NAME_PACKAGE_NAME + " TEXT," +
            Alarm.COLUMN_NAME_PACKAGE_NUMBER + " INTEGER" +
            " )";
    private static final String SQL_CREATE_NUMBER =  "CREATE TABLE " + Alarm.TABLE_NAME_N + " (" +
            Alarm._ID + " INTEGER," +
            Alarm.COLUMN_NAME_NUMBER_NUMBER + " INTEGER" +
            " )";
    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;
    //package
    private static final String SQL_DELETE_PACKAGE =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME_P;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
        db.execSQL(SQL_CREATE_PACKAGE);
        db.execSQL(SQL_CREATE_NUMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        db.execSQL(SQL_DELETE_PACKAGE);
        db.execSQL(SQL_CREATE_NUMBER);
        onCreate(db);
    }


    private AlarmModel populateModel(Cursor c) {
        AlarmModel model = new AlarmModel();
        model.id = c.getLong(c.getColumnIndex(Alarm._ID));
        model.name = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_NAME));
        model.timeHour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_HOUR));
        model.timeMinute = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE));

        model.timeHourend = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_HOUR_END));
        model.timeMinuteend = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE_END));

        model.repeatWeekly = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY)) == 0 ? false : true;
        model.alarmTone = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TONE)) != "" ? Uri.parse(c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TONE))) : null;
        model.isEnabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_ENABLED)) == 0 ? false : true;
        model.number =  c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_NUMBER));
        String[] repeatingDays = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS)).split(",");
        for (int i = 0; i < repeatingDays.length; ++i) {
            model.setRepeatingDay(i, repeatingDays[i].equals("false") ? false : true);
        }

        return model;
    }

    private ContentValues populateContent(AlarmModel model) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_HOUR, model.timeHour);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, model.timeMinute);

        values.put(Alarm.COLUMN_NAME_ALARM_TIME_HOUR_END, model.timeHourend);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE_END, model.timeMinuteend);

        values.put(Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY, model.repeatWeekly);
        values.put(Alarm.COLUMN_NAME_ALARM_TONE, model.alarmTone != null ? model.alarmTone.toString() : "");
        values.put(Alarm.COLUMN_NAME_ALARM_ENABLED, model.isEnabled);
        values.put(Alarm.COLUMN_NAME_ALARM_NUMBER,model.number);
        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
            repeatingDays += model.getRepeatingDay(i) + ",";
        }
        values.put(Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS, repeatingDays);

        return values;
    }

    //package
    private ContentValues populateContentPackage(String name,int number) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_PACKAGE_NAME, name);
        values.put(Alarm.COLUMN_NAME_PACKAGE_NUMBER, number);
        return values;
    }

    private ContentValues populateContentNumber(int number,int id) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_NUMBER_NUMBER, number);
        values.put(Alarm._ID, id);
        return values;
    }
    public long createAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
    }

    //package
    public long createPackage(String name,int number) {
        ContentValues values = populateContentPackage(name, number);

        return getWritableDatabase().insert(Alarm.TABLE_NAME_P, null, values);
    }
    public long createNumber(int number,int id) {
        ContentValues values = populateContentNumber(number,id);

        return getWritableDatabase().insert(Alarm.TABLE_NAME_N, null, values);
    }
    public long updateAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[]{String.valueOf(model.id)});
    }

    //package
    public long updatePackage(String name,long id,int number) {
        ContentValues values = populateContentPackage(name, number);
        return getWritableDatabase().update(Alarm.TABLE_NAME_P, values,Alarm._ID + " = ?", new String[] { String.valueOf(id) });
    }

    public long updateNumber(int id,int number) {
        ContentValues values = populateContentNumber(number, id);
        return getWritableDatabase().update(Alarm.TABLE_NAME_N, values, Alarm._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }

        return null;
    }

    //package
    public String getPackage(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME_P + " WHERE " + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_PACKAGE_NAME));
        }

        return null;
    }
    //package
    public int getNumber(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME_N + " WHERE " + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_NUMBER_NUMBER));
        }

        return 0;
    }

    public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public List<AlarmModel> getAlarms(int number) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm.COLUMN_NAME_ALARM_NUMBER + " = " + number;

        Cursor c = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }
    //package
    public List<String> getPackage() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME_P;

        Cursor c = db.rawQuery(select, null);

        List<String> packageList = new ArrayList<String>();

        while (c.moveToNext()) {
            packageList.add( c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_PACKAGE_NAME)));
        }

        if (!packageList.isEmpty()) {
            return packageList;
        }

        return null;
    }
    public String[] getPackage(int number) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME_P+ " WHERE " + Alarm.COLUMN_NAME_PACKAGE_NUMBER + " = " + number;

        Cursor c = db.rawQuery(select, null);

        List<String> packageList = new ArrayList<String>();

        while (c.moveToNext()) {
            packageList.add( c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_PACKAGE_NAME)));
        }

        if (!packageList.isEmpty()) {
            String[] names = new String[packageList.size()];
            names = packageList.toArray(names);
            return names;
        }

        return null;
    }
    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " =?", new String[] { String.valueOf(id) });
    }

    public int deletePackage(String packagename,int number) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME_P,Alarm.COLUMN_NAME_PACKAGE_NUMBER + " =? and "+Alarm.COLUMN_NAME_PACKAGE_NAME+" =?", new String[] {String.valueOf(number),packagename});

    }
}
