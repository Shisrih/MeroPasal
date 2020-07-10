package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meropasal.R;

public class Signup extends AppCompatActivity {
 private TextView signuptxt;
 private ImageView signupclip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signuptxt =findViewById(R.id.signuptxt);
        signupclip = findViewById(R.id.signupclip);

        Animation txt = AnimationUtils.loadAnimation(this,R.anim.zoomout);
        signuptxt.startAnimation(txt);

        Animation clip = AnimationUtils.loadAnimation(this,R.anim.blink);
        signupclip.startAnimation(clip);
    }
}