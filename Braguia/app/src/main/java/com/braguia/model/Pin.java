package com.braguia.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pin",indices = @Index(value = {"id"},unique = true))
public class Pin {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name="id")
    String id;

    @SerializedName("pin_imgs")
    @ColumnInfo(name="pin_imgs")
    String urls;  // exemplo: "url1; url2;"

    @ColumnInfo(name="pin_name")
    @SerializedName("pin_name")
    String pin_name;

    @ColumnInfo(name="pin_desc")
    @SerializedName("pin_desc")
    String pin_desc;

    @ColumnInfo(name="pin_lat")
    @SerializedName("pin_lat")
    String pin_lat;

    @ColumnInfo(name="pin_lng")
    @SerializedName("pin_lng")
    String pin_lng;

    @ColumnInfo(name="pin_alt")
    @SerializedName("pin_alt")
    String pin_alt;

    @NonNull
    public String getId() {
        return id;
    }

    public String getUrls() {return urls;}

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

    public Pin(@NonNull String id, String urls, String pin_name, String pin_desc,
               String pin_lat, String pin_lng, String pin_alt) {
        this.id = id;
        this.urls = urls;
        this.pin_name = pin_name;
        this.pin_desc = pin_desc;
        this.pin_lat = pin_lat;
        this.pin_lng = pin_lng;
        this.pin_alt = pin_alt;
    }
}
