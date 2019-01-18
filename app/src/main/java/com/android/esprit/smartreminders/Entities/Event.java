package com.android.esprit.smartreminders.Entities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Event extends AbstractEventOrTask implements Entity {
    protected Time StartTime;
    protected Time EndTime;
    protected int Reminder;


    public Event() {
        super();
    }

    public Event(StateOfTask state, String description, Set<DayOfTheWeek> days, Time StartTime, Time EndTime, int Reminder,User owner) {
        super(state, description, days ,owner);
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Reminder = Reminder;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time endTime) {
        EndTime = endTime;
    }

    public int getReminder() {
        return Reminder;
    }

    public void setReminder(int reminder) {
        Reminder = reminder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Event event = (Event) o;
        return Objects.equals(StartTime, event.StartTime) &&
                Objects.equals(EndTime, event.EndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), StartTime, EndTime);
    }

    @Override
    public String toString() {
        return "Event{" +
                super.toString() +
                "StartTime=" + StartTime +
                ", EndTime=" + EndTime +
                '}';
    }


    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask {
        super.FromJsonObject(ja);
        String seconds;
        String minutes;
        String hours;
        String StringArray[];

        StringArray = ja.get("startTime").toString().split(":");
        hours = StringArray[0];
        minutes = StringArray[1];
        this.StartTime = new Time(Integer.valueOf(hours), Integer.valueOf(minutes));

        StringArray = ja.get("endTime").toString().split(":");
        hours = StringArray[0];
        minutes = StringArray[1];
        this.EndTime = new Time(Integer.valueOf(hours), Integer.valueOf(minutes));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return super.ToJsonObject()
                .put("startTime", this.StartTime)
                .put("endTime", this.EndTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = super.ToPostMap();
        res.put("startTime", this.StartTime.toString());
        res.put("endTime", this.EndTime.toString());
        return res;
    }
}
