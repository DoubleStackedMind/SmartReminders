package com.android.esprit.smartreminders.Services;


import android.content.Context;
import android.util.Log;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class WebServiceEvent extends WebServiceConsumer<Event> {
    public WebServiceEvent(Context parentActivity, CallBackWSConsumer<Event> Behaviour) {
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
