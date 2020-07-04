package com.example.meropasal_designs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Logindashboard extends AppCompatActivity {
Button pwdbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindashboard);
        pwdbtn = findViewById(R.id.passwordsignin);

        pwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logindashboard.this,MainLogin.class);
                startActivity(intent);
            }
        });
    }
}