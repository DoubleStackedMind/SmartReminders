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
    private String url;

    public WebServiceUser(Activity c, CallBackWSConsumer<User> b) {
        super(c, b);
        url = this.parentActivity.getString(R.string.url_root);
        url += "user/";
        Log.d("Constructor", "WebServiceUser[url is]:" + url);
    }

    @Override
    public int insert(User user) {
        String localUrl = url + "new";
        Consume(localUrl, user);
        return 200;
    }

    @Override
    public int update(User user) {
        String localUrl = url +user.getId() + "/edit";
        Consume(localUrl, user);
        return 200;
    }

    @Override
    public int remove(User user) {
        String localUrl = url + +user.getId() + "/delete";
        Consume(localUrl, user);
        return 200;
    }

    @Override
    public void findBy(Map<String, String> columnAndValue) {
        StringBuilder localUrl = new StringBuilder(url);
        for (Map.Entry<String, String> one : columnAndValue.entrySet()) {
            if (localUrl.toString().endsWith("/"))// beginning of the get attributes
            {
                localUrl.append("?").append(one.getKey()).append("=").append(one.getValue());
            } else // after adding the first attribue you pass an &
            {
                localUrl.append("&").append(one.getKey()).append("=").append(one.getValue());
            }
        }
        ConsumeAndWait(localUrl.toString(), Request.Method.GET);
    }

    @Override
    public void fetch(Map<String, String> columnAndValue) {

        StringBuilder localUrl = new StringBuilder(url + "all/");
        for (Map.Entry<String, String> one : columnAndValue.entrySet()) {
            if (localUrl.toString().endsWith("/"))// beginning of the get attributes
            {
                localUrl.append("?").append(one.getKey()).append("=").append(one.getValue());
            } else // after adding the first attribue you pass an &
            {
                localUrl.append("&").append(one.getKey()).append("=").append(one.getValue());
            }
        }
        ConsumeAndWait(localUrl.toString(), Request.Method.GET);
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
