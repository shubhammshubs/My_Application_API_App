package com.example.myapplication.Classes;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Fregement_Activitiy.ProfileFregment;
import com.example.myapplication.R;
import com.example.myapplication.models.Employee;
import com.example.myapplication.services.EmployeeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WelcomeActivity extends AppCompatActivity {

    String mobile = MyApplication.mobile;
    String username;
    String fname;
    Button button,button1;

    public WelcomeActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fetchUsername();


        button = findViewById(R.id.profile_fregment);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfragement (new ProfileFregment());
            }

        });

        button1 = findViewById(R.id.Logout_fregment);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
// Calling Freagement Activity
    private void openfragement(Fragment profileFragement) {

        FragmentManager fragmentManager = getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout,profileFragement);
        fragmentTransaction.commit();
    }

    private void fetchUsername() {

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apip.trifrnd.com/portal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a Retrofit service for the API interface
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

        // Call the API to get the attendance data for the mobile number
        Call<List<Employee>> call =employeeApi.getAllEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> employees =response.body();

                    Employee targetEmployee = null;
                    for (Employee employee : employees) {
                        if (employee.getMobile().equals(mobile)){
                            targetEmployee = employee;
                            break;
                        }
                    }
                    TextView nameTextView = findViewById(R.id.nameTextView);
                    nameTextView.setText(targetEmployee.getFname());
                }

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });

    }
}