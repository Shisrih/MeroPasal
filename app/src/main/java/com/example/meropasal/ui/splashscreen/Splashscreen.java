package com.example.meropasal.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.meropasal.ui.home.Dashboard;
import com.example.meropasal.R;

public class Splashscreen extends AppCompatActivity {
    ImageView logo,clipart;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        logo = findViewById(R.id.Applogo);



        Animation animate = AnimationUtils.loadAnimation(this,R.anim.transition);
        logo.startAnimation(animate);





        handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(Splashscreen.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        },1500);


    }
}