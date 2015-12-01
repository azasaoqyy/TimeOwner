package com.trigg.alarmclock;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 智堯 on 2015/11/4.
 */
public class PackageDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";

    private static final String SQL_CREATE_PACKAGE = "CREATE TABLE " + "package" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "package_name" + " TEXT," +
            "number" + " INTEGER," +

            " );";

    private static final String SQL_DELETE_PACKAGE =
            "DROP TABLE IF EXISTS " +  "package";

    public PackageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PACKAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PACKAGE);
        onCreate(db);
    }




}
