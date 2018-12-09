package com.android.esprit.smartreminders.Services;


import android.app.Activity;
import android.util.Log;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.volley.Request;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Map;

public class WebServiceUser extends WebServiceConsumer<User> {


    public WebServiceUser(Activity parentActivity, CallBackWSConsumer<User> Behaviour) {
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
