package com.trigg.alarmclock;

/**
 * Created by user on 2015/11/20.
 */
public class Setting {
    private String title;
    private int iconID;
    private String subtitle;

    public Setting(String title, int iconID, String subtitle) {
        super();
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public int geticonID() {
        return iconID;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
