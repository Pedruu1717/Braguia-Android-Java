package com.braguia.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("auth_Token")
    public String authToken;

    @SerializedName("session_id")
    public String sessionId;

}
