package cn.colorfuline.elderlylauncher.bean;

import java.io.Serializable;

/**
 * Created by CQDXP on 2016/9/28.
 */

public class LocationBean implements Serializable {
    public static final String KEY = "locationBean";
    private String city;
    private String Province;
    private double longitude;
    private double latitude;

    public LocationBean(String city, String province, double longitude, double latitude) {
        this.city = city;
        Province = province;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
