package com.example.myapplication;

public class Employee {
//    private String id;
    private String fname;
    private String lname;
    private String EmpId;
    private String email;
    private String mobile;

    private String department_name;


    public Employee(String fname, String lname, String empId, String email, String mobile, String department_name) {
        this.fname = fname;
        this.lname = lname;
        EmpId = empId;
        this.email = email;
        this.mobile = mobile;
        this.department_name = department_name;
    }

    //    private String department_name;

//    public String getDepartment_name() {
//        return department_name;
//    }

//    public String getId() {
//        return id;
//    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmpId() {
        return EmpId;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFullName() {
        return fname + " " + lname;
    }
}
