package com.android.esprit.smartreminders.Entities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Task extends AbstractEventOrTask implements Entity {
    protected Set<Action> actions;

    public Task() {
    }

    public Task(StateOfTask state, String description, Set<DayOfTheWeek> days) {
        super(state, description, days);
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Task{" +
                "actions=" + actions +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", days=" + days +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(actions, task.actions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), actions);
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask {
        super.FromJsonObject(ja);
        Set<Action> actions = new HashSet<>();
        JSONArray jsa = (JSONArray) ja.get("actions");
        for (int i = 0; i < jsa.length(); i++) {
            Action a=new Action();
            a.FromJsonObject((JSONObject) jsa.get(i));
            actions.add(a);
        }
        this.actions.addAll(actions);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public JSONObject ToJsonObject() throws JSONException {
        JSONArray actions = new JSONArray();
        this.actions.forEach((a) -> {
            try {
                actions.put(a.ToJsonObject());
            } catch (JSONException j) {
                Log.d("Entity :Task", "ToJsonObject: " + j.getMessage());
            }
        });
        return super.ToJsonObject().put("actions", actions);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res=super.ToPostMap();
        JSONArray actions = new JSONArray();
        this.actions.forEach((a) -> {
            try {
                actions.put(a.ToJsonObject());
            } catch (JSONException j) {
                Log.d("Entity :EventOrTask", "ToJsonObject: " + j.getMessage());
            }
        });
        res.put("actions", actions.toString());
        return res;
    }
}
