package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmDetailsActivity extends Activity {
	
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	private AlarmModel alarmDetails;
	
	private TimePicker timePicker;
	private TimePicker timePickerend;
	private EditText edtName;
	private CheckBox chkWeekly;
	private CheckBox chkSunday;
	private CheckBox chkTuesday;
	private CheckBox chkMonday;
	private CheckBox chkWednesday;
	private CheckBox chkThursday;
	private CheckBox chkFriday;
	private CheckBox chkSaturday;
	private TextView txtToneSelection;
	private Button SaveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_details);

		//getActionBar().setTitle("Create New Alarm");
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		timePickerend = (TimePicker) findViewById(R.id.alarm_details_time_end_picker);

		edtName = (EditText) findViewById(R.id.alarm_details_name);

		chkWeekly = (CheckBox) findViewById(R.id.alarm_details_repeat_weekly);
		chkSunday = (CheckBox) findViewById(R.id.alarm_details_repeat_sunday);
		chkMonday = (CheckBox) findViewById(R.id.alarm_details_repeat_monday);
		chkTuesday = (CheckBox) findViewById(R.id.alarm_details_repeat_tuesday);
		chkWednesday = (CheckBox) findViewById(R.id.alarm_details_repeat_wednesday);
		chkThursday = (CheckBox) findViewById(R.id.alarm_details_repeat_thursday);
		chkFriday = (CheckBox) findViewById(R.id.alarm_details_repeat_friday);
		chkSaturday = (CheckBox) findViewById(R.id.alarm_details_repeat_saturday);
		txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
		
		long id = getIntent().getExtras().getLong("id");
		
		if (id == -1) {
			alarmDetails = new AlarmModel();
		} else {
			alarmDetails = dbHelper.getAlarm(id);
			
			timePicker.setCurrentMinute(alarmDetails.timeMinute);
			timePicker.setCurrentHour(alarmDetails.timeHour);

			timePickerend.setCurrentMinute(alarmDetails.timeMinuteend);
			timePickerend.setCurrentHour(alarmDetails.timeHourend);
			
			edtName.setText(alarmDetails.name);
			
			chkWeekly.setChecked(alarmDetails.repeatWeekly);
			chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
			chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
			chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
			chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
			chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
			chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRDIAY));
			chkSaturday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

			txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
		}

		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				startActivityForResult(intent , 1);
			}
		});

		SaveButton = (Button) findViewById(R.id.save_button);
		SaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateModelFromLayout();

				AlarmManagerHelper.cancelAlarms(AlarmDetailsActivity.this);
                AlarmManagerHelperEnd.cancelAlarms(AlarmDetailsActivity.this);

				if (alarmDetails.id < 0) {
					dbHelper.createAlarm(alarmDetails);
				} else {
					dbHelper.updateAlarm(alarmDetails);
				}

				AlarmManagerHelper.setAlarms(AlarmDetailsActivity.this);
                AlarmManagerHelperEnd.setAlarms(AlarmDetailsActivity.this);

				setResult(RESULT_OK);
				Intent startIntent = new Intent(AlarmDetailsActivity.this, MainActivity.class);
				startActivity(startIntent);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        switch (requestCode) {
		        case 1: {
		        	alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		        	txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
		            break;
		        }
		        default: {
		            break;
		        }
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home: {
				finish();
				break;
			}
			case R.id.action_save_alarm_details: {
				updateModelFromLayout();
				
				AlarmManagerHelper.cancelAlarms(this);
				
				if (alarmDetails.id < 0) {
					dbHelper.createAlarm(alarmDetails);
				} else {
					dbHelper.updateAlarm(alarmDetails);
				}
				
				AlarmManagerHelper.setAlarms(this);
				
				setResult(RESULT_OK);
				finish();
			}
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void updateModelFromLayout() {		
		alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		alarmDetails.timeHour = timePicker.getCurrentHour().intValue();

        alarmDetails.timeMinuteend = timePickerend.getCurrentMinute().intValue();
        alarmDetails.timeHourend = timePickerend.getCurrentHour().intValue();

		alarmDetails.name = edtName.getText().toString();
		alarmDetails.repeatWeekly = chkWeekly.isChecked();	
		alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, chkFriday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());
		alarmDetails.isEnabled = true;
	}
	
}
