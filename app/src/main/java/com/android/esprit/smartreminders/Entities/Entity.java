package com.android.esprit.smartreminders.Entities;

import org.json.JSONArray;

import java.util.Map;

public interface Entity {
    public Entity FromJsonArray(JSONArray ja);
    public JSONArray ToJsonArray(Entity e);
    public Map<String,String> ToPostMap(Entity e);
}
