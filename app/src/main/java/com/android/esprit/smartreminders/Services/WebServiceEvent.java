package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;
import com.android.esprit.smartreminders.R;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class WebServiceEvent extends WebServiceConsumer<Event> {
    public WebServiceEvent(Activity parentActivity, CallBackWSConsumer<Event> Behaviour) {
        super(parentActivity, Behaviour);
        url += "event/";
        Log.d("Constructor", "WebServiceEvent[url is]:" + url);
    }
    @Override
    public void ResponseBody(String response) {
        entities = new ArrayList<>();
        try {
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                Event e = new Event();
                e.FromJsonObject(j.getJSONObject(i));
                entities.add(e);
            }
        } catch (JSONException |NotAValidStateOfTask e ) {
            e.printStackTrace();
        }
    }
}
