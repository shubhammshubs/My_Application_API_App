package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ResetPassword {

    @FormUrlEncoded
    @POST("data.php?apicall=chngpwd")
    Call<String> resate(@Field("mobile") String mobile, @Field("password") String password,
                        @Field("newpwd") String newpwd);
}
