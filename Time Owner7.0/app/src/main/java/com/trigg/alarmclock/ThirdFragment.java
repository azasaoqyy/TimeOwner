package com.trigg.alarmclock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by encore on 2015/7/20.
 */
public class ThirdFragment extends Fragment {

    private List<Setting> list = new ArrayList<Setting>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list,container,false);
        //listView = (ListView)view.findViewById(R.id.list);
        /*set();
        list();
        click();*/
        ListView listView = (ListView)view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();

                if (position == 0) {
                    dialog.setTitle("Title");
                    dialog.setMessage("Book1 Selected");
                } else if (position == 2) {
                    Intent startIntent = new Intent(getActivity(), Setting_Introduction.class);
                    startActivity(startIntent);
                }

                dialog.show();
            }

        });

        ArrayAdapter<Setting> adapter = new MyListAdapter();
        listView.setAdapter(adapter);

        //Resources resources = getResources();

        list.add(new Setting(getString(R.string.Change_Password), R.drawable.password, "\n" + getString(R.string.Change_PasswordD)));
        list.add(new Setting(getString(R.string.Remind), R.drawable.remind, "\n" + getString(R.string.RemindD)));
        list.add(new Setting(getString(R.string.Introduction), R.drawable.introduction, "\n" + getString(R.string.IntroductionD)));
        list.add(new Setting(getString(R.string.About_us), R.drawable.icon, ""));


        return view;
    }


    private class MyListAdapter extends ArrayAdapter<Setting> {

        public MyListAdapter() {
            super(getActivity(), R.layout.item, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.item, parent, false);
            }

            Setting currentSetting = list.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.image1);
            imageView.setImageResource(currentSetting.geticonID());

            TextView title = (TextView)itemView.findViewById(R.id.texttitle);
            title.setText(currentSetting.getTitle());

            TextView subtitle = (TextView)itemView.findViewById(R.id.textsubtitle);
            subtitle.setText(currentSetting.getSubtitle());

            return itemView;
        }
    }


}

