package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Zone implements Entity {

    private int id;
    private String name;
    private double lng;
    private double lat;
    private double radius;
    private User owner;

    public Zone() {
    }

    public Zone(String name, double lng, double lat, double radius, User owner) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.radius = radius;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return id == zone.id &&
                Double.compare(zone.lng, lng) == 0 &&
                Double.compare(zone.lat, lat) == 0 &&
                Double.compare(zone.radius, radius) == 0 &&
                Objects.equals(name, zone.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, lng, lat, radius);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", radius=" + radius +
                '}';
    }

    @Override
    public void FromJsonObject(JSONObject ja) throws JSONException {
        this.id = ja.getInt("id");
        this.name = ja.getString("name");
        this.lng = ja.getDouble("lng");
        this.lat = ja.getDouble("lat");
        this.radius = ja.getDouble("radius");
        this.owner =new User();
        this.owner.setId(ja.getInt("user"));
    }

    @Override
    public JSONObject ToJsonObject() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("lng", lng)
                .put("lat", lat)
                .put("user",owner.getId())
                .put("radius", radius);
    }

    @Override
    public Map<String, String> ToPostMap() {
        Map<String, String> res = new HashMap<>();
        res.put("id", id + "");
        res.put("name", name + "");
        res.put("lng", lng + "");
        res.put("lat", lat + "");
        res.put("radius", radius + "");
        res.put("user",owner.getId()+"");
        System.out.println(owner.getId()+"erfgdsqdsfsdfsdqfsqd");
        return res;

    }
}
