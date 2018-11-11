package com.android.esprit.smartreminders.Services;


import android.content.Context;
import android.support.v4.util.Pools;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebServiceUser extends WebServiceConsumer<User> {
    private String url;

    public WebServiceUser(Context c) {
        super(c);
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
        String localUrl = url + +user.getId()+"/edit";
        Consume(localUrl, user);
        return 200;
    }

    @Override
    public int remove(User user) {
        String localUrl = url + +user.getId()+"/delete";
        Consume(localUrl, user);
        return 200;
    }

    @Override
    public User findBy(Map<String, String> columnAndValue) throws InterruptedException {
        String localUrl = url ;
        for (Map.Entry<String, String> one : columnAndValue.entrySet()) {
            if (localUrl.endsWith("/"))// beginning of the get attributes
            {
                localUrl += "?" + one.getKey() + "=" + one.getValue();
            } else // after adding the first attribue you pass an &
            {
                localUrl += "&" + one.getKey() + "=" + one.getValue();
            }
        }
        ConsumeAndWait(localUrl, Request.Method.GET);// this will notify the object when done
        synchronized (this) {// will wait until notified
            wait();
            if (entities.isEmpty())
                return null;
            return entities.get(0);
        }

    }


    @Override
    public List<User> fetch(Map<String, String> columnAndValue) throws InterruptedException {
        String localUrl = url + "all/";
        for (Map.Entry<String, String> one : columnAndValue.entrySet()) {
            if (localUrl.endsWith("/"))// beginning of the get attributes
            {
                localUrl += "?" + one.getKey() + "=" + one.getValue();
            } else // after adding the first attribue you pass an &
            {
                localUrl += "&" + one.getKey() + "=" + one.getValue();
            }
        }
        ConsumeAndWait(localUrl, Request.Method.GET);// this will notify the object when done
        synchronized (this) {// will wait until notified
            wait();
            if (entities.isEmpty())
                return null;
            return entities;
        }
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
