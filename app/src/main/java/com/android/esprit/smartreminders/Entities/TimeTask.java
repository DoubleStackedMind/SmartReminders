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

public class TimeTask extends Task implements Entity {
    protected Time executionTime;

    public TimeTask(Time executionTime) {
        this.executionTime = executionTime;
    }

    public TimeTask(StateOfTask state, String description, Set<DayOfTheWeek> days, Time executionTime,User owner ) {
        super(state, description, days,owner);
        this.executionTime = executionTime;
    }

    public TimeTask() {
    }

    public Time getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Time executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TimeTask timeTask = (TimeTask) o;
        return Objects.equals(executionTime, timeTask.executionTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), executionTime);
    }

    @Override
    public String toString() {
        return "TimeTask{" +
                "executionTime=" + executionTime +
                ", actions=" + actions +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", days=" + days +
                '}';
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask {
        super.FromJsonObject(ja);
        String seconds;
        String minutes;
        String hours;
        String StringArray[];

        StringArray = ja.get("executionTime").toString().split(":");
        hours = StringArray[0];
        minutes = StringArray[1];
        this.executionTime = new Time(Integer.valueOf(hours), Integer.valueOf(minutes));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return super.ToJsonObject().put("executionTime", this.executionTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = super.ToPostMap();
        res.put("executionTime", this.executionTime.toString());
        return res;
    }
}
