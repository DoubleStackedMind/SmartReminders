package com.android.esprit.smartreminders.Entities;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class User implements Entity {
    private int id;
    private String email;
    private String password;
    public User(){}
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {

        return id * 195 + email.hashCode() + password.hashCode();
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException {

                this.id=ja.getInt("id");
                this.email=ja.getString("email");
                this.password=ja.getString("password");

    }

    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return
                new JSONObject()
                        .put("id", this.id)
                        .put("email", this.email)
                        .put("password", this.password);
    }

    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = new HashMap<>();

        res.put("email", this.email);
        res.put("id", this.id + "");
        res.put("password", this.password);
        return res;
    }
}
