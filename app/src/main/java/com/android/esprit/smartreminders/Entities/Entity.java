package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public interface Entity {
      void FromJsonObject(JSONObject ja) throws JSONException, NotAValidStateOfTask;
     JSONObject ToJsonObject() throws JSONException;
     Map<String,String> ToPostMap();
}
