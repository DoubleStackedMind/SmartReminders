package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class WebServiceTimeTask extends WebServiceConsumer<TimeTask> {
    public WebServiceTimeTask(Context parentActivity, CallBackWSConsumer<TimeTask> Behaviour) {
        super(parentActivity, Behaviour);
        url += "timetask/";
        Log.d("Constructor", "WebServiceTimeTask[url is]:" + url);
    }
    @Override
    public void ResponseBody(String response) {
        entities = new ArrayList<>();
        try {
            System.out.println(response);
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                TimeTask t = new TimeTask();
                t.FromJsonObject(j.getJSONObject(i));
                entities.add(t);
            }
        } catch (JSONException |NotAValidStateOfTask e ) {
            e.printStackTrace();
        }
    }
}
