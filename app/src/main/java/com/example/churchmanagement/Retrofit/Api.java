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

    @POST(APIClient.APPEND_URL + "getAllMinistry")
    Call<JsonObject> getAllMinistry(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "updateMinistry")
    Call<JsonObject> updateMinistry(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "del_ministry")
    Call<JsonObject> del_ministry(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "addMinistryRequest")
    Call<JsonObject> addMinistryRequest(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getMinistryRequestStatus")
    Call<JsonObject> getMinistryRequestStatus(@Body JsonObject object);







}
