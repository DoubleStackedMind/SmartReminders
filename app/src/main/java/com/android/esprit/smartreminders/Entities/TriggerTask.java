package com.android.esprit.smartreminders.Entities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;
import com.android.esprit.smartreminders.customControllers.ActionPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TriggerTask extends Task implements Entity {
    protected Set<Trigger> triggers;

    public TriggerTask(Set<Trigger> triggers) {
        this.triggers = triggers;
    }

    public TriggerTask(StateOfTask state, String description, Set<DayOfTheWeek> days, Set<Trigger> triggers) {
        super(state, description, days);
        this.triggers = triggers;
    }

    public TriggerTask() {
    }

    public Set<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(Set<Trigger> triggers) {
        this.triggers = triggers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TriggerTask that = (TriggerTask) o;
        return Objects.equals(triggers, that.triggers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), triggers);
    }

    @Override
    public String toString() {
        return "TriggerTask{" +
                "triggers=" + triggers +
                ", actions=" + actions +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", days=" + days +
                '}';
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask {
        super.FromJsonObject(ja);
        Set<Action> actions = new HashSet<>();
        JSONArray jsa = (JSONArray) ja.get("actions");
        for (int i = 0; i < jsa.length(); i++) {
            Action a = new Action(){

                @Override
                public void executeAction() {

                }
            };
            a.FromJsonObject((JSONObject) jsa.get(i));
            actions.add(a);
        }
        this.actions.addAll(actions);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public JSONObject ToJsonObject() throws JSONException {
        JSONArray triggers = new JSONArray();
        this.actions.forEach((t) -> {
            try {
                triggers.put(t.ToJsonObject());
            } catch (JSONException j) {
                Log.d("Entity :TriggerTask", "ToJsonObject: " + j.getMessage());
            }
        });
        return super.ToJsonObject().put("triggers", triggers);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = super.ToPostMap();
        JSONArray triggers = new JSONArray();
        this.triggers.forEach((t) -> {
            try {
                triggers.put(t.ToJsonObject());
            } catch (JSONException j) {
                Log.d("Entity :TriggerTask", "ToJsonObject: " + j.getMessage());
            }
        });
        res.put("triggers", triggers.toString());
        return res;
    }
}
