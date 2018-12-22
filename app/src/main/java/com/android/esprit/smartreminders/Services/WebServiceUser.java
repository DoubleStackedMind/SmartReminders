package com.android.esprit.smartreminders.Services;


import android.content.Context;
import android.util.Log;
import com.android.esprit.smartreminders.Entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class WebServiceUser extends WebServiceConsumer<User> {


    public WebServiceUser(Context parentActivity, CallBackWSConsumer<User> Behaviour) {
        super(parentActivity, Behaviour);
        url += "user/";
        Log.d("Constructor", "WebServiceUser[url is]:" + url);
    }
    @Override
    public void ResponseBody(String response) {
        entities = new ArrayList<>();
        try {
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                User u = new User();
                u.FromJsonObject(j.getJSONObject(i));
                entities.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
