package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import com.example.myapplication.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFregment extends Fragment {
    String mobile = MyApplication.mobile;

    private SharedPreferences pref;

    private TextView nameTextView,empIdTextView,emailTextView,mobileTextView;




    public ProfileFregment() {
        // Required empty public constructor
    }
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_profile_fregment, container, false);
//        initViews(view);
//        return view;
//    }
//
//
//    public void onViewCreated(View view,Bundle savedInstanceState) {
//        pref =getActivity().getPreferences(0);
//        nameTextView.setText(pref.getString(Util.NAME,""));
//        empIdTextView.setText(pref.getString(Util.empId,""));
//        emailTextView.setText(pref.getString(Util.EMAIL,""));
//        mobileTextView.setText(pref.getString(Util.mobile,""));
//
//    }
//
//    private void initViews(View view) {
//        TextView nameTextView =(TextView) view.findViewById(R.id.nameTextView);
//        TextView empIdTextView =(TextView) view.findViewById(R.id.empIdTextView);
//        TextView emailTextView =(TextView) view.findViewById(R.id.emailTextView);
//        TextView mobileTextView =(TextView) view.findViewById(R.id.mobileTextView);
////        TextView departmentTextView=(TextView) view.findViewById(R.id.departmentTextView);
//
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fregment, container, false);

        // Create the Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apip.trifrnd.in/portal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of your EmployeeApi interface
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

        // Make the API call
        Call<List<User>> call = employeeApi.getAllEmployees();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> employees = response.body();
                    // Find the Employee with the matching mobile number
                    User targetEmployee = null;
                    for (User employee : employees) {
                        if (employee.getMobile().equals(mobile)) {
                            targetEmployee = employee;
                            break;
                        }
                    }
                    // Populate the views in your profile fragment with the data from the matched Employee object
                    TextView nameTextView = view.findViewById(R.id.nameTextView);
                    TextView empIdTextView = view.findViewById(R.id.empIdTextView);
                    TextView emailTextView = view.findViewById(R.id.emailTextView);
                    TextView mobileTextView = view.findViewById(R.id.mobileTextView);
                    TextView departmentTextView=view.findViewById(R.id.departmentTextView);

//                    nameTextView.setText(targetEmployee.getFullName());
                    empIdTextView.setText(targetEmployee.getEmpId());
                    emailTextView.setText(targetEmployee.getEmail());
                    mobileTextView.setText(targetEmployee.getMobile());
                    departmentTextView.setText(targetEmployee.getDepartment_name());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                try {
                    Toast.makeText(WelcomeActivity.class.newInstance(), "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (java.lang.InstantiationException e) {
                    throw new RuntimeException(e);
                }


            }
        });


        return view;
    }
}