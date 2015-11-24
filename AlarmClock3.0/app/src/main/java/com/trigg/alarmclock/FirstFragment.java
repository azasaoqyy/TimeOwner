package com.trigg.alarmclock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by encore on 2015/7/20.
 */
public class FirstFragment extends Fragment {

    private AlarmListAdapter mAdapter;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(getActivity());
    private Context mContext;
    private Button NextButton;
    private ListView listView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_layout,container,false);



        /*view = inflater.inflate(R.layout.activity_alarm_list,container,false);
        listView = (ListView)view.findViewById(R.id.list);

        mContext = getActivity();

        //requestWindowFeature(Window.FEATURE_ACTION_BAR);

        mAdapter = new AlarmListAdapter(getActivity(), dbHelper.getAlarms());

        listView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            mAdapter.setAlarms(dbHelper.getAlarms());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setAlarmEnabled(long id, boolean isEnabled) {
        AlarmManagerHelper.cancelAlarms(getActivity());

        AlarmModel model = dbHelper.getAlarm(id);
        model.isEnabled = isEnabled;
        dbHelper.updateAlarm(model);

        AlarmManagerHelper.setAlarms(getActivity());
    }

    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(getActivity(), AlarmDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    public void deleteAlarm(long id) {
        final long alarmId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please confirm")
                .setTitle("Delete set?")
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel Alarms
                        AlarmManagerHelper.cancelAlarms(mContext);
                        //Delete alarm from DB by id
                        dbHelper.deleteAlarm(alarmId);
                        //Refresh the list of the alarms in the adaptor
                        mAdapter.setAlarms(dbHelper.getAlarms());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Set the alarms
                        AlarmManagerHelper.setAlarms(mContext);
                    }
                }).show();*/
    }




    
}
