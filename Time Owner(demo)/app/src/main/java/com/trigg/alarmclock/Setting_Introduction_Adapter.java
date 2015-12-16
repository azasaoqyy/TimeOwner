package com.trigg.alarmclock;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ResourceBundle;

/**
 * Created by user on 2015/11/25.
 */
public class Setting_Introduction_Adapter extends PagerAdapter {

    private int [] image_resources = {R.drawable.set1, R.drawable.set2, R.drawable.set3, R.drawable.set4};
    private Context context;
    private LayoutInflater layoutInflater;

    public Setting_Introduction_Adapter (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image_resources.length;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(image_resources[position]);
        TextView textView = (TextView) view.findViewById(R.id.text);
        switch (position)
        {
            case 0:
                textView.setText("主畫面利用TAB\n" +
                        "可選擇計畫表顯示app禁用時\n" +
                        "間表。\n");
                break;

            case 1:
                textView.setText("新增禁用時間段\n" +
                        "選擇要留下的應用程式，也能\n" +
                        "新增模式方便之後使用。\n");
                break;

            case 2:
                textView.setText("決定可使用的app後設定禁用\n" +
                        "時間與日期後完成。\n");
                break;

            case 3:
                textView.setText("進階設定功能\n");
                break;

        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }
}
