package com.example.myapplication.Fregement_Activitiy;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.myapplication.models.Employee;
import com.example.myapplication.Classes.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.services.EmployeeApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFregment extends Fragment {
    String mobile = MyApplication.mobile;

    private SharedPreferences pref;

//    private TextView nameTextView,empIdTextView,emailTextView,mobileTextView;




    public ProfileFregment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fregment, container, false);

        // Create the Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apip.trifrnd.com/portal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of your EmployeeApi interface
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

        // Make the API call
        Call<List<Employee>> call =employeeApi.getAllEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> employees = response.body();
                    // Find the Employee with the matching mobile number
                    Employee targetEmployee = null;
                    for (Employee employee : employees) {
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

                    nameTextView.setText(targetEmployee.getFullName());
                    empIdTextView.setText(targetEmployee.getEmpId());
                    emailTextView.setText(targetEmployee.getEmail());
                    mobileTextView.setText(targetEmployee.getMobile());
                    departmentTextView.setText(targetEmployee.getDepartment_name());
                }

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });


        return view;
    }
}