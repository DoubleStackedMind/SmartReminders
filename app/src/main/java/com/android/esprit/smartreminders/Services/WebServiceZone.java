package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Entities.Zone;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class WebServiceZone extends WebServiceConsumer<Zone> {
    public WebServiceZone(Context parentActivity, CallBackWSConsumer<Zone> Behaviour) {
        super(parentActivity, Behaviour);
        url += "zone/";
        Log.d("Constructor", "WebServiceZone[url is]:" + url);
    }
    @Override
    public void ResponseBody(String response) {
        System.out.println(response);
        entities = new ArrayList<>();
        try {
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                Zone z = new Zone();
                z.FromJsonObject(j.getJSONObject(i));
                entities.add(z);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
