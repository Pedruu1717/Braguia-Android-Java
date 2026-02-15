package com.braguia.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;



/* Includes Edges */
public class TrailResponse {

    @SerializedName("id")
    String id;

    @SerializedName("trail_img")
    String image_url;

    @SerializedName("trail_name")
    String trail_name;

    @SerializedName("trail_desc")
    String trail_desc;

    @SerializedName("trail_duration")
    Integer trail_duration;

    @SerializedName("trail_difficulty")
    String trail_difficulty;

    @SerializedName("edges")
    ArrayList<EdgeResponse> edges;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTrail_name() {
        return trail_name;
    }

    public String getTrail_desc() {
        return trail_desc;
    }

    public Integer getTrail_duration() {
        return trail_duration;
    }

    public String getTrail_difficulty() {
        return trail_difficulty;
    }

    public ArrayList<EdgeResponse> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrailResponse trail = (TrailResponse) o;
        return id.equals(trail.id) &&
                Objects.equals(image_url, trail.image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_url);
    }

}
