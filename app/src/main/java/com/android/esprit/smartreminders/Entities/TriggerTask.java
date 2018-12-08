package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TriggerTask extends Task implements Entity{
    protected Set<Trigger> triggers;

    public TriggerTask(Set<Trigger> triggers) {
        this.triggers = triggers;
    }

    public TriggerTask(StateOfTask state, String description, Set<DayOfTheWeek> days, Set<Trigger> triggers) {
        super(state, description, days);
        this.triggers = triggers;
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
