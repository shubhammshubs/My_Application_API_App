package com.example.myapplication.Classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private String phoneNumber;
    private TextView mobileText,forget_Password;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView forgetPass =(TextView)findViewById(R.id.forget_Pass);

        forgetPass.setMovementMethod(LinkMovementMethod.getInstance());
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, ForegetPassword.class);
                startActivity(intent);
            }
        });

//      Code for all necessary Permissions.
        if (!checkPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
                || !checkPermission(android.Manifest.permission.INTERNET)
                || !checkPermission(android.Manifest.permission.READ_SMS)
                || !checkPermission(android.Manifest.permission.READ_PHONE_STATE)
                || !checkPermission(android.Manifest.permission.READ_PHONE_NUMBERS)) {
            // Request the required permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.READ_PHONE_NUMBERS
            }, REQUEST_CODE_PERMISSIONS);
        } else {
            setupActivity();
        }
        // Set the text of the TextView to the phone number, if it's available
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            retrieveMobileNumber();
        }
    }
//  Get Mobile Number directly to Phone number
    private void retrieveMobileNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
        } else {
            String fullNumber = telephonyManager.getLine1Number();
            if (fullNumber != null && fullNumber.length() > 2) {
                phoneNumber = fullNumber.substring(fullNumber.length()-10);
            } else {
                phoneNumber = "";
            }
        }
    }
//  Check for Permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                setupActivity();
            } else {
                // permission denied, close the app
                Toast.makeText(this, "Permissions are required to use this app", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void setupActivity() {
        mobileText = findViewById(R.id.mobile_textview);
        passwordEditText = findViewById(R.id.password_edittext);
        Button loginButton = findViewById(R.id.login_button);


        retrieveMobileNumber();
        mobileText.setText(phoneNumber);

        loginButton.setOnClickListener(v -> {
            String mobile = mobileText.getText().toString();
            String password = passwordEditText.getText().toString();

            MyApplication.mobile = mobile;

            if (mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter both mobile and password", Toast.LENGTH_SHORT).show();
            } else {
                // Create a Bundle to pass mobile to SignInFragment
                Bundle bundle = new Bundle();
                bundle.putString("mobile", mobile);

                // Create a Retrofit instance
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://apip.trifrnd.com/portal/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                // Create a LoginService instance using the Retrofit instance
                LoginService service = retrofit.create(LoginService.class);
                // Call the login API with the given mobile and password
                Call<String> call = service.login(mobile, password);
                // Handle the response
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful()) {
                            String loginResponse = response.body();
                            if (loginResponse != null && loginResponse.equals("Login Done")) {

//

                                // Login successful, start the DashboardActivity
                                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Login failed, display an error message
                                String errorMessage = "Invalid username or password";
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Login failed, display an error message
                            String errorMessage = "Unable to login. Please try again later";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        // Login failed, display an error message
                        String errorMessage = "Unable to connect to server. Please try again later";
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }




    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}