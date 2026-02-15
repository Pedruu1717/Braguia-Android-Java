package com.braguia.model;

import com.google.gson.annotations.SerializedName;

public class EdgeResponse {

    @SerializedName("id")
    String id;

    @SerializedName("edge_start")
    Pin edge_start;

    @SerializedName("edge_end")
    Pin edge_end;

    @SerializedName("edge_transport")
    String edge_transport;

    @SerializedName("edge_duration")
    String edge_duration;

    @SerializedName("edge_desc")
    String edge_desc;

    @SerializedName("edge_trail")
    String edge_trail;

    public String getId() {
        return id;
    }

    public Pin getEdge_start() {
        return edge_start;
    }

    public Pin getEdge_end() {
        return edge_end;
    }

    public String getEdge_transport() {
        return edge_transport;
    }

    public String getEdge_duration() {
        return edge_duration;
    }

    public String getEdge_desc() {
        return edge_desc;
    }

    public String getEdge_trail() {
        return edge_trail;
    }
}
