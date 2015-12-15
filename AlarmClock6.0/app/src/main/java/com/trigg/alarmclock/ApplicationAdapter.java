package com.trigg.alarmclock;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationAdapter extends BaseAdapter {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;
    boolean[] itemChecked;
    boolean[] itemCheckedss;

    public ApplicationAdapter(Context context, int textViewResourceId,
                              List<ApplicationInfo> appsList,boolean[] itemCheckeds) {

        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
        itemCheckedss = itemCheckeds;
        itemChecked = new boolean[appsList.size()];
        if( itemCheckedss==null) {
            Log.d("d","d");
        }
        else {
            itemChecked=itemCheckedss;
            Log.d("ds","ds");
        }

    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (null == view) {

            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_application_adapter, null);
        }

        ApplicationInfo data = appsList.get(position);
        if (null != data) {
            TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
            final CheckBox ch = (CheckBox) view.findViewById(R.id.checkBox);
            ch.setFocusableInTouchMode(false);
            ch.setFocusable(false);
            ch.setChecked(false);

            if (itemChecked[position])
                ch.setChecked(true);
            else
               ch.setChecked(false);

            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ch.isChecked())
                        itemChecked[position] = true;
                    else
                        itemChecked[position] = false;
                }
            });
            appName.setText(data.loadLabel(packageManager));
            packageName.setText( data.packageName);
            iconview.setImageDrawable(data.loadIcon(packageManager));
        }
        return view;
    }
};
