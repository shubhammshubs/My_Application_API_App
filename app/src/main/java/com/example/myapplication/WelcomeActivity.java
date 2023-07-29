package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    Button button;

//    public WelcomeActivity() {
//        // Required empty public constructor
//    }

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
                .baseUrl("https://apip.trifrnd.in/portal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a Retrofit service for the API interface
        LoginService attendanceService = retrofit.create(LoginService.class);

        // Call the API to get the attendance data for the mobile number
        Call<String> call = attendanceService.getUsername(mobile,fname);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    username =response.body();
                    TextView userNameTextView = findViewById(R.id.username);
                    userNameTextView.setText(username);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "Failed to fetch username", Toast.LENGTH_SHORT).show();


            }
        });
    }
}