package com.trigg.alarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

public class AlarmDetailsActivity extends ActionBarActivity {
	
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	private AlarmModel alarmDetails;
	private int number=1;
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
				SharedPreferences setting= getSharedPreferences("loginInfo", 0);
				String job = setting.getString("job", "");
				updateModelFromLayout();

				AlarmManagerHelper.cancelAlarms(AlarmDetailsActivity.this);
                AlarmManagerHelperEnd.cancelAlarms(AlarmDetailsActivity.this);

				if (alarmDetails.id < 0) {
					if(job.equals("n")) {
						dbHelper.createAlarm(alarmDetails);
					}
					else if(job.equals("m")){

						Toast.makeText(AlarmDetailsActivity.this,Boolean.toString(alarmDetails.repeatWeekly),Toast.LENGTH_LONG).show();

						boolean[] t = alarmDetails.getRepeatingDays();
						String f =Boolean.toString(t[0])+Boolean.toString(t[1])+Boolean.toString(t[2])+Boolean.toString(t[3])+Boolean.toString(t[4])+Boolean.toString(t[5])+Boolean.toString(t[6]);
						Alarm_work aw = new Alarm_work(AlarmDetailsActivity.this);
						aw.execute("save",
								alarmDetails.name,
								Integer.toString(alarmDetails.timeHour),
								Integer.toString(alarmDetails.timeMinute),
								Integer.toString(alarmDetails.timeHourend),
								Integer.toString(alarmDetails.timeMinuteend),
								f,
								Boolean.toString(alarmDetails.repeatWeekly),
								"0",
								Boolean.toString(alarmDetails.isEnabled),
								Integer.toString(alarmDetails.number));
					}
				} else {
					dbHelper.updateAlarm(alarmDetails);
				}

				AlarmManagerHelper.setAlarms(AlarmDetailsActivity.this);
                AlarmManagerHelperEnd.setAlarms(AlarmDetailsActivity.this);

				setResult(RESULT_OK);

				SharedPreferences setting2= getSharedPreferences("loginInfo", 0);
				int user_id = setting2.getInt("user_id", 0);
				Intent it = new Intent();
				it.setClass(AlarmDetailsActivity.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("user_id", user_id);
				it.putExtras(bundle);
				startActivity(it);
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

		/*switch (item.getItemId()) {
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
		}*/

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
		alarmDetails.number=dbHelper.getNumber(0)-1;

	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Alarm_work extends AsyncTask<String, String, String> {

		Context ctx;
		Alarm_work(Context ctx){
			this.ctx = ctx;
		}


		@Override
		protected String doInBackground(String... params) {
			String alarm_save_url = "http://120.126.16.70/kai/bjt/alarm_save.php";
			String job_set_url = "http://120.126.16.70/kai/bjt/job_set.php";
			String method = params[0];
			if (method.equals("save")){
				String name = params[1];
				String hour = params[2];
				String minute = params[3];
				String hour_end = params[4];
				String minute_end = params[5];
				String days = params[6];
				String weekly = params[7];
				String tone = params[8];
				String enabled = params[9];
				String number = params[10];

				try {
					URL url = new URL(alarm_save_url);
					HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setDoInput(true);

					///
					OutputStream OS = httpURLConnection.getOutputStream();
					BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
					String data = URLEncoder.encode("name", "UTF-8") + "=" +URLEncoder.encode(name,"UTF-8") + "&" +
							URLEncoder.encode("hour", "UTF-8") + "=" +URLEncoder.encode(hour,"UTF-8") + "&" +
							URLEncoder.encode("minute", "UTF-8") + "=" +URLEncoder.encode(minute,"UTF-8") + "&" +
							URLEncoder.encode("hour_end", "UTF-8") + "=" +URLEncoder.encode(hour_end,"UTF-8") + "&" +
							URLEncoder.encode("minute_end", "UTF-8") + "=" +URLEncoder.encode(minute_end,"UTF-8") + "&" +
							URLEncoder.encode("days", "UTF-8") + "=" +URLEncoder.encode(days,"UTF-8") + "&" +
							URLEncoder.encode("weekly", "UTF-8") + "=" +URLEncoder.encode(weekly,"UTF-8") + "&" +
							URLEncoder.encode("tone", "UTF-8") + "=" +URLEncoder.encode(tone,"UTF-8") + "&" +
							URLEncoder.encode("enabled", "UTF-8") + "=" +URLEncoder.encode(enabled,"UTF-8") + "&" +
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
