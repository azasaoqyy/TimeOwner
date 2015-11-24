/*package com.trigg.alarmclock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyListAdapter extends ArrayAdapter<Setting> {

    public MyListAdapter() {
            super(this, R.layout.item, list);
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
}*/
