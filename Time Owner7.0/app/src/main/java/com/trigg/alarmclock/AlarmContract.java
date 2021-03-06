package com.trigg.alarmclock;

import android.provider.BaseColumns;

public final class AlarmContract {
	
	public AlarmContract() {}
	
	public static abstract class Alarm implements BaseColumns {
		public static final String TABLE_NAME = "alarm";
		public static final String COLUMN_NAME_ALARM_NAME = "name";
		public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
		public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";

		public static final String COLUMN_NAME_ALARM_TIME_HOUR_END = "hour_end";
		public static final String COLUMN_NAME_ALARM_TIME_MINUTE_END = "minute_end";

		public static final String COLUMN_NAME_ALARM_REPEAT_DAYS = "days";
		public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "weekly";
		public static final String COLUMN_NAME_ALARM_TONE = "tone";
		public static final String COLUMN_NAME_ALARM_ENABLED = "enabled";
		public static final String COLUMN_NAME_ALARM_NUMBER = "number";

		public static final String TABLE_NAME_P = "package";
		public static final String COLUMN_NAME_PACKAGE_NAME = "name";
		public static final String COLUMN_NAME_PACKAGE_NUMBER = "number";

		public static final String TABLE_NAME_N = "number";

		public static final String COLUMN_NAME_NUMBER_NUMBER = "number";
	}
	
}
