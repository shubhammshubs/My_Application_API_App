package com.example.myapplication;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("Attend.php?apicall=login")
    Call<String> login(@Field("mobile") String mobile, @Field("password") String password);
}
