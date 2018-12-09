package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public interface Entity {
    public  void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask;
    public JSONObject ToJsonObject() throws JSONException;
    public Map<String,String> ToPostMap();
}
