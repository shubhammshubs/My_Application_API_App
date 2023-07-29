package com.example.myapplication;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static final String BASE_URL = "https://apip.trifrnd.in/portal/";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String empId = "empId";
    public static final String mobile = "mobile";


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
