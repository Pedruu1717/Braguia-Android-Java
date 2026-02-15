package com.braguia.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name="id")
    Integer id;

    //@NonNull
    @ColumnInfo(name="email")
    @SerializedName("email")
    String email;

    //@NonNull
    @ColumnInfo(name="password")
    @SerializedName("password")
    String password;

    //@PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    @SerializedName("username")
    String username;

    @NonNull
    @ColumnInfo(name = "user_type")
    @SerializedName("user_type")
    String user_type;

    @ColumnInfo(name="last_login")
    @SerializedName("last_login")
    String last_login;

    @ColumnInfo(name="is_superuser")
    @SerializedName("is_superuser")
    boolean is_superuser;

    @ColumnInfo(name="first_name")
    @SerializedName("first_name")
    String first_name;

    @ColumnInfo(name="last_name")
    @SerializedName("last_name")
    String last_name;

    @ColumnInfo(name="is_staff")
    @SerializedName("is_staff")
    boolean is_staff;

    @ColumnInfo(name="is_active")
    @SerializedName("is_active")
    boolean is_active;

    @ColumnInfo(name="date_joined")
    @SerializedName("date_joined")
    String date_joined;

//    @ColumnInfo(name="groups")
//    @SerializedName("groups")
//    String groups;
//
//    @ColumnInfo(name="user_permissions")
//    @SerializedName("user_permissions")
//    String user_permissions;

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
        this.user_type = "standard";
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setUser_type(@NonNull String user_type) {
        this.user_type = user_type;
    }

    public void setEmail(@NonNull String email) {this.email = email;}

    public void setPassword(@NonNull String password) {this.password = password;}

    @NonNull
    public String getEmail() {return email;}

    @NonNull
    public String getPassword() {return password;}

    public String getUsername() {
        return username;
    }

    @NonNull
    public String getUserType() {
        return user_type;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public boolean getIsStaff() {
        return is_staff;
    }

    public String getDateJoined() {
        return date_joined;
    }


}
