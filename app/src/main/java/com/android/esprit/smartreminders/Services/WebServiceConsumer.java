package com.android.esprit.smartreminders.Services;

import android.util.Log;

import com.android.esprit.smartreminders.Entities.Entity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WebServiceConsumer<T extends Entity> {
    public int insert(T t);

    public int update(T t);

    public int remove(T t);

    public T findBy(String arg);

    public List<T> fetch(String[] args);// arguments can null

    public String ResponseBody();

    public String RequestBody();

    public default void Consume(String url, int method, T t) {
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
                return new HashMap<String, String>(t.ToPostMap(t));
            }
        };
        RequestQueue queue = Volley.newRequestQueue(null);
        queue.add(postRequest);
    }

    public default void ConsumeAndWait(String url, int method) {

        StringRequest stringRequest = new StringRequest(
                method,
                url,
                response -> {
                    ResponseBody();
                },
                error -> {
                    Log.d("WebService:" + this.getClass().getName() + "[error]:", "That didn't work !");
                }
        );
        RequestQueue queue = Volley.newRequestQueue(null);
        queue.add(stringRequest);
    }
}
