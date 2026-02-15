package com.braguia.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("user")
    Call<User> getUser(@Header("Cookie") String tokenAndSessionId);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("logout")
    Call<LogoutResponse> logout();
}
