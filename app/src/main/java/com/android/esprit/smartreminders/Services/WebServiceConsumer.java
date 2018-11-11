package com.android.esprit.smartreminders.Services;

import android.content.Context;
import android.util.Log;

import com.android.esprit.smartreminders.Entities.Entity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebServiceConsumer<T extends Entity> {
    protected Context parentActivity;
    protected List<T>entities;

    public Context getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(Context parentActivity) {
        this.parentActivity = parentActivity;
    }

    public List<T> getEntities() {
        return entities;
    }

    public abstract int insert(T t);

    public abstract int update(T t);

    public abstract int remove(T t);

    public abstract T findBy(String arg);

    public abstract List<T> fetch(String[] args);// arguments can null

    public abstract void ResponseBody(String response);



    public WebServiceConsumer(Context parentActivity) {
        this.parentActivity=parentActivity;
    }


    public  void Consume(String url, T t) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("WebService:" + this.getClass().getName() + "[response]:", "Done With Result Ok");
                },
                error -> {
                    Log.d("WebService:" + this.getClass().getName() + "[error]:", "That didn't work !");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<String, String>(t.ToPostMap());
            }
        };
        RequestQueue queue = Volley.newRequestQueue(parentActivity);
        queue.add(postRequest);
    }

    public  void ConsumeAndWait(String url, int method) {

        StringRequest stringRequest = new StringRequest(
                method,
                url,
                response -> {
                    ResponseBody(response);
                },
                error -> {
                    Log.d("WebService:" + this.getClass().getName() + "[error]:", "That didn't work !");
                }
        );
        RequestQueue queue = Volley.newRequestQueue(parentActivity);
        queue.add(stringRequest);
    }
}
