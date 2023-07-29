package com.example.myapplication;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("data.php?apicall=login")
    Call<String> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("data.php?apicall=read")
    Call<String> getUsername(
            @Field("mobile") String mobile ,
             @Field("fname") String fname
    );

}
