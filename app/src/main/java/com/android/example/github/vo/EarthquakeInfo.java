package com.android.example.github.vo;

public class EarthquakeInfo {

    private String date, magnitude, location, depth;

    public EarthquakeInfo(String date, String magnitude, String location, String depth) {
        this.date = date;
        this.magnitude = magnitude;
        this.location = location;
        this.depth = depth;
    }

    public EarthquakeInfo() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }
}
