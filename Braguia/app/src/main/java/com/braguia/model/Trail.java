package com.braguia.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "trail",indices = @Index(value = {"id"},unique = true))
public class Trail {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("trail_img")
    @ColumnInfo(name = "trail_img")
    String image_url;

    @SerializedName("trail_name")
    @ColumnInfo(name = "trail_name")
    String trail_name;

    @SerializedName("trail_desc")
    @ColumnInfo(name = "trail_desc")
    String trail_desc;

    @SerializedName("trail_duration")
    @ColumnInfo(name = "trail_duration")
    Integer trail_duration;

    @SerializedName("trail_difficulty")
    @ColumnInfo(name = "trail_difficulty")
    String trail_difficulty;

    public Trail(@NonNull String id, String image_url, String trail_name, String trail_desc, Integer trail_duration, String trail_difficulty) {
        this.id = id;
        this.image_url = image_url;
        this.trail_name = trail_name;
        this.trail_desc = trail_desc;
        this.trail_duration = trail_duration;
        this.trail_difficulty = trail_difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return image_url;
    }

    public void setUrl(String url) {
        this.image_url = url;
    }

    public String getTrailName() {
        return trail_name;
    }

    public String getTrailDesc() {
        return trail_desc;
    }

    public Integer getTrailDuration() {
        return trail_duration;
    }

    public String getTrailDifficulty() {
        return trail_difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return id.equals(trail.id) &&
                Objects.equals(image_url, trail.image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_url);
    }
}
