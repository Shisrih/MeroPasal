package com.example.meropasal.ui.acount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.meropasal.R;

public class EditProfileDash extends AppCompatActivity {

    private LinearLayout editprofile,editlogincred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editprofile = findViewById(R.id.edituserinfo);
        editlogincred = findViewById(R.id.editlogincredentials);


        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileDash.this,EditUserInfo.class);
                startActivity(intent);
            }
        });


        editlogincred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileDash.this,EditLogincredentials.class);
                startActivity(intent);
            }
        });


    }
}