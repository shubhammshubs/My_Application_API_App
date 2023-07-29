package com.example.myapplication;

import java.util.List;
import com.example.myapplication.Employee;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmployeeApi {
    @POST("data.php?apicall=read")
    default Call<List<User>> getAllEmployees() {
        return null;
    }
}