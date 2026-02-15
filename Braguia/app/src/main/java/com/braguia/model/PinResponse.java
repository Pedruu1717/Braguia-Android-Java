package com.braguia.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class PinResponse {

    @SerializedName("id")
    String id;

    @SerializedName("rel_pin")
    ArrayList<Map<String,Object>> rel_pin;

    @SerializedName("media")
    ArrayList<Map<String,Object>> media;

    @SerializedName("pin_name")
    String pin_name;

    @SerializedName("pin_desc")
    String pin_desc;

    @SerializedName("pin_lat")
    String pin_lat;

    @SerializedName("pin_lng")
    String pin_lng;

    @SerializedName("pin_alt")
    String pin_alt;

    public ArrayList<Map<String, Object>> getRel_pin() {
        return rel_pin;
    }

    public ArrayList<Map<String, Object>> getMedia() {
        return media;
    }

    public String getId() {
        return id;
    }

    public String getPin_name() {
        return pin_name;
    }

    public String getPin_desc() {
        return pin_desc;
    }

    public String getPin_lat() {
        return pin_lat;
    }

    public String getPin_lng() {
        return pin_lng;
    }

    public String getPin_alt() {
        return pin_alt;
    }
}
