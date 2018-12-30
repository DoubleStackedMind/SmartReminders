package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.ZoneTriggerType;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class ZoneTask extends Task implements Entity {
    private Zone zone;
    private ZoneTriggerType triggerType;
    public ZoneTask(){super();}
    public ZoneTask(Zone zone,ZoneTriggerType triggerType){
        this.zone=zone;
        this.triggerType=triggerType;
    }

    public Zone getZone() {
        return zone;
    }

    public ZoneTriggerType getTriggerType() {
        return triggerType;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setTriggerType(ZoneTriggerType triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ZoneTask zoneTask = (ZoneTask) o;
        return Objects.equals(zone, zoneTask.zone) &&
                triggerType == zoneTask.triggerType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), zone, triggerType);
    }

    @Override
    public String toString() {
        return "ZoneTask{" +
                "zone=" + zone +
                ", triggerType=" + triggerType +
                ", actions=" + actions +
                ", id=" + id +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", days=" + days +
                '}';
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask {
        super.FromJsonObject(ja);
        zone= new Zone();
        this.zone.FromJsonObject((JSONObject)ja.get("zone"));
        this.triggerType=ZoneTriggerType.valueOf(ja.get("triggerType").toString());
    }

    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return super.ToJsonObject()
                .put("zone",this.zone.ToJsonObject())
                .put("triggerType",this.triggerType.toString());
    }

    @Override
    public Map<String, String> ToPostMap() {
        Map<String ,String> map= super.ToPostMap();
        try {
            map.put("zone",this.zone.ToJsonObject().toString());
            map.put("triggerType",this.triggerType.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
