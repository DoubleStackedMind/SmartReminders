package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.TriggerTask;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class WebServiceTriggerTask extends WebServiceConsumer<TriggerTask> {
    public WebServiceTriggerTask(Activity parentActivity, CallBackWSConsumer<TriggerTask> Behaviour) {
        super(parentActivity, Behaviour);
        url += "triggertask/";
        Log.d("Constructor", "WebServiceTriggerTask[url is]:" + url);
    }
    @Override
    public void ResponseBody(String response) {
        entities = new ArrayList<>();
        try {
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                TriggerTask t = new TriggerTask();
                t.FromJsonObject(j.getJSONObject(i));
                entities.add(t);
            }
        } catch (JSONException|NotAValidStateOfTask e) {
            e.printStackTrace();
        }
    }
}
