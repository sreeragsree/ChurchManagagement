package com.example.churchmanagement.Retrofit;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST(APIClient.APPEND_URL + "addMinistry")
    Call<JsonObject> addMinistry(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "register")
    Call<JsonObject> register(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "login")
    Call<JsonObject> login(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllMembers")
    Call<JsonObject> getAllMembers(@Body JsonObject object);


}
