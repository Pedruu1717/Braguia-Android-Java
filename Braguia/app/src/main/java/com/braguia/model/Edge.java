package com.braguia.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "edge",indices = @Index(value = {"id"},unique = true))
public class Edge {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("edge_start")
    @ColumnInfo(name = "edge_start")
    String edge_start;

    @SerializedName("edge_end")
    @ColumnInfo(name = "edge_end")
    String edge_end;

    @SerializedName("edge_transport")
    @ColumnInfo(name = "edge_transport")
    String edge_transport;

    @SerializedName("edge_duration")
    @ColumnInfo(name = "edge_duration")
    String edge_duration;

    @SerializedName("edge_desc")
    @ColumnInfo(name = "edge_desc")
    String edge_desc;

    @SerializedName("edge_trail")
    @ColumnInfo(name = "edge_trail")
    String edge_trail;

    public Edge(@NonNull String id, String edge_start, String edge_end, String edge_transport, String edge_duration, String edge_desc, String edge_trail) {
        this.id = id;
        this.edge_start = edge_start;
        this.edge_end = edge_end;
        this.edge_transport = edge_transport;
        this.edge_duration = edge_duration;
        this.edge_desc = edge_desc;
        this.edge_trail = edge_trail;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getEdge_start() {
        return edge_start;
    }

    public String getEdge_end() {
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
