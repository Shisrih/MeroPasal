package com.example.meropasal.utiils;

import android.content.SharedPreferences;

public class Authenticator {

    public static final boolean checkLoginStatus(SharedPreferences sharedPreferences){


        if(sharedPreferences.getString(Constants.TOKEN, null) == null){
            return false;
        }else{
            return true;
        }
    }
}
