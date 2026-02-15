package com.braguia.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PinAPI {

    @GET("pins")
    Call<List<PinResponse>> getPins(@Header("Cookie") String tokenAndSessionId);

}
