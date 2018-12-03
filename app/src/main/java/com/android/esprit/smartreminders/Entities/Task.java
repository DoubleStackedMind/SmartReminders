package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Map;
import java.util.Objects;

public class Task extends AbstractEventOrTask implements Entity{
    protected Time executionTime;

    public Task() {
    }

    public Task(StateOfTask state, String description, Time executionTime) {
        super(state, description);
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
        Task task = (Task) o;
        return Objects.equals(executionTime, task.executionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), executionTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                super.toString()+
                "executionTime=" + executionTime +
                '}';
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException {
        try {
            this.state=StateOfTask.fromString(ja.getString("state"));
            this.executionTime.setTime(ja.getLong("executionTime"));
            this.description=ja.getString("description");
        } catch (NotAValidStateOfTask notAValidStateOfTask) {
            notAValidStateOfTask.printStackTrace();
        }
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
