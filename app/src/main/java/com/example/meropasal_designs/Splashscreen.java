package com.example.meropasal_designs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splashscreen extends AppCompatActivity {
    ImageView logo,clipart;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        },1700);


    }
}