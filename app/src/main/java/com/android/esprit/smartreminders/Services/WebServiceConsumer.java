package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.android.esprit.smartreminders.Entities.Entity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebServiceConsumer<T extends Entity> {

    protected Activity parentActivity;//where the webService was called Essential to Extract Context
    protected List<T> entities;
    protected CallBackWSConsumer<T> Behaviour;
    public void SetBehaviour(CallBackWSConsumer<T> callBack ){
        this.Behaviour=callBack;
    }
    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;

    }

    public List<T> getEntities() {
        return entities;
    }

    public abstract int insert(T t);

    public abstract int update(T t);

    public abstract int remove(T t);

    public abstract void findBy(Map<String, String> columnAndValue) throws InterruptedException;

    public abstract void fetch(Map<String, String> columnAndValue) throws InterruptedException;// arguments can null

    public abstract void ResponseBody(String response);


    public WebServiceConsumer(Activity parentActivity, CallBackWSConsumer<T> Behaviour) {
        this.parentActivity = parentActivity;
        this.Behaviour = Behaviour;
    }


    public void Consume(String url, T t) {
        Log.d("Url", "Consume: Url Used :" + url);
        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    System.out.println(response);
                    String result="";
                    try {
                       JSONObject ja=new JSONObject(response);
                        result=ja.get("result").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (result.equals("ok")) {
                        this.Behaviour.OnResultPresent();
                        Log.d("WebService:" + this.getClass().getName() + "[response]:", "Done With Result Ok");
                    } else
                        this.Behaviour.OnResultNull();

                },
                error -> {
                    Log.d("WebService:" + this.getClass().getName() + "[error]:", "That didn't work !");
                    this.Behaviour.OnHostUnreachable();
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

    public void ConsumeAndWait(String url, int method) {
        Log.d("Url", "ConsumeAndWait: Url Used :" + url);
        StringRequest stringRequest = new StringRequest(
                method,
                url,
                response -> {
                    ResponseBody(response);
                    if (entities.isEmpty())
                        this.Behaviour.OnResultNull();
                    else
                        this.Behaviour.OnResultPresent(entities);
                },
                error -> {
                    Log.d("WebService:" + this.getClass().getName() + "[error]:", "That didn't work !");
                    this.Behaviour.OnHostUnreachable();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(parentActivity);
        queue.add(stringRequest);
    }

}




