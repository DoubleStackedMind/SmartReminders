package com.android.esprit.smartreminders.Entities;

public class Time {
    private int hour;
    private int minute;

    public Time() {
        this.hour = 0;
        this.minute =0;
    }
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        if(minute < 10 && hour < 10)
            return "0"+hour+":0"+minute;
        if(hour < 10)
            return "0"+hour+":"+minute;
        if(minute<10)
            return hour+":0"+minute;
       return hour+":"+minute;
    }
}
