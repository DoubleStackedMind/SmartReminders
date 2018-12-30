package com.android.esprit.smartreminders.Entities;



import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public  class User implements Entity {
    private int id;
    private String email;
    private String name;
    private String password;
    private DailyPlan dailyplan;
    private Set<AbstractEventOrTask>Plans;
    private Set<Zone> zones;
    public User(){}
    public User(int id, String email, String password,String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DailyPlan getDailyplan(){ return this.dailyplan;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password);
    }

    public void setDailyplan(DailyPlan dailyplan) {
        this.dailyplan = dailyplan;
    }

    public Set<AbstractEventOrTask> getPlans() {
        return Plans;
    }

    public void setPlans(Set<AbstractEventOrTask> plans) {
        Plans = plans;
    }

    public Set<Zone> getZones() {
        return zones;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, name, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException {

        this.id=ja.getInt("id");
        this.email=ja.getString("email");
        this.password=ja.getString("password");
        this.name=ja.getString("name");

    }

    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return
                new JSONObject()
                        .put("id", this.id)
                        .put("email", this.email)
                        .put("password", this.password)
                        .put("name",this.name);
    }

    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = new HashMap<>();
        res.put("email", this.email);
        res.put("id", this.id + "");
        res.put("password", this.password);
        res.put("name",this.name);
        return res;
    }

}
