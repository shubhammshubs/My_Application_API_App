package com.example.myapplication;

public class User {

    private String fname;
    private String lname;
    private String EmpId;
    private String email;
    private String mobile;

    private String department_name;

    public User(String fname, String lname, String empId, String email, String mobile, String department_name) {
        this.fname = fname;
        this.lname = lname;
        EmpId = empId;
        this.email = email;
        this.mobile = mobile;
        this.department_name = department_name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}

