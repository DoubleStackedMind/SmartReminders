package com.android.esprit.smartreminders.Services;


import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebServiceUser extends WebServiceConsumer<User> {
    private String url;
    public WebServiceUser(Context c)
    {
        super(c);
        url=this.parentActivity.getString(R.string.url_root);
        url+="user/";
        Log.d("Constructor", "WebServiceUser[url is]:"+url);
    }
    @Override
    public int insert(User user) {
        String localUrl=url+"new";
        Consume(localUrl,user);
       return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int remove(User user) {
        return 0;
    }

    @Override
    public User findBy(String arg) {
        return null;
    }

    @Override
    public List<User> fetch(String[] args) {
        return null;
    }

    @Override
    public void ResponseBody(String response) {
        entities=new ArrayList<>();
        try {
            JSONArray j= new JSONArray(response);
            for(int i=0;i<j.length();i++) {
            User u = new User();
            u.FromJsonObject(j.getJSONObject(i));
            entities.add(u);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
