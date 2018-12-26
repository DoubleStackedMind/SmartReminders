package com.android.esprit.smartreminders.sessions;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.esprit.smartreminders.Entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Session {
    private User sessionUser;
    private static Session session;
    private Context context;

    private Session(Context context) {
        this.context = context;
        SharedPreferences sharedPref = context.getSharedPreferences("Myprefs", MODE_PRIVATE);
        String data = sharedPref.getString("Logged_user_data", "User name or data missing");
        if(!data.equals("User name or data missing")) {
            this.sessionUser = new User();
            try {
                this.sessionUser.FromJsonObject(new JSONObject(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public User getSessionUser() {
        return this.sessionUser;
    }

    public void setSessionUser(User user) {
        session.sessionUser = sessionUser;
    }

    public static Session getSession(Context context) {
        if (session == null||session.sessionUser==null) {
            session = new Session(context);
        }
        return session;
    }
}
