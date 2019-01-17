package com.android.esprit.smartreminders.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.android.esprit.smartreminders.Entities.Entity;
import com.android.esprit.smartreminders.R;
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

    protected Context parentActivity;//where the webService was called Essential to Extract Context
    protected List<T> entities;
    protected CallBackWSConsumer<T> Behaviour;
    protected String url;
    public void SetBehaviour(CallBackWSConsumer<T> callBack ){
        this.Behaviour=callBack;
    }
    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;


    }
    public List<T> getEntities() {
        return entities;
    }

    public  int insert(T t){
        String localUrl = url + "new";
        Consume(localUrl, t);
        return 200;
    }

    public  int update(T t) {
        String localUrl = url + "edit";
        Consume(localUrl, t);
        return 200;
    }

    public int remove(T t) {
        String localUrl = url + "delete";
        Consume(localUrl, t);
        return 200;
    }

    public  void findBy(Map<String, String> columnAndValue) throws InterruptedException {
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

    public  void fetch(Map<String, String> columnAndValue) throws InterruptedException {// arguments can null

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

    public abstract void ResponseBody(String response);


    public WebServiceConsumer(Context parentActivity, CallBackWSConsumer<T> Behaviour) {
        this.parentActivity = parentActivity;
        this.Behaviour = Behaviour;
        url = this.parentActivity.getString(R.string.url_root);
    }


    public void Consume(String url, T t) {
     /*   StringBuilder localUrl= new StringBuilder(url);
        Map<String, String> columnAndValue= t.ToPostMap();
        for (Map.Entry<String, String> one : columnAndValue.entrySet()) {
            if (localUrl.toString().endsWith("w"))// beginning of the get attributes
            {
                localUrl.append("?").append(one.getKey()).append("=").append(one.getValue());
            } else // after adding the first attribue you pass an &
            {
                localUrl.append("&").append(one.getKey()).append("=").append(one.getValue());
            }
        } */
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




