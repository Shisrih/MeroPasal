package com.example.meropasal.ui.acount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.meropasal.R;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;

public class EditUserInfo extends AppCompatActivity {

    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        checkAuth();





    }

    private void checkAuth(){
        if(!Authenticator.checkLoginStatus(sharedPreferences)){
            finish();
            startActivity(new Intent(this, Logindashboard.class));
        }
    }


}