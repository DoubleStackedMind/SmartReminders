package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TimeTask extends Task implements Entity{
    protected Time executionTime;

    public TimeTask(Time executionTime) {
        this.executionTime = executionTime;
    }

    public TimeTask(StateOfTask state, String description, Set<DayOfTheWeek> days, Time executionTime) {
        super(state, description, days);
        this.executionTime = executionTime;
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
    public void FromJsonObject(JSONObject ja) throws JSONException {

    }

    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return null;
    }

    @Override
    public Map<String, String> ToPostMap() {
        return null;
    }
}
