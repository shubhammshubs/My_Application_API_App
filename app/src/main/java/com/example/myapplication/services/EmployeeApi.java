package com.example.myapplication.services;


import com.example.myapplication.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmployeeApi {

    @GET("data.php?apicall=readall")
    Call<List<Employee>> getAllEmployees();


    @FormUrlEncoded
    @POST("data.php?apicall=read")
    Call<String> getUsername(@Field("mobile") String mobile);


}
