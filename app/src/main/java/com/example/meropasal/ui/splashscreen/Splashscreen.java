package com.example.meropasal.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.meropasal.ui.home.Dashboard;
import com.example.meropasal.R;

public class Splashscreen extends AppCompatActivity {
    ImageView logo,clipart;
    Handler handler;
    boolean connected = false;
    Dialog dialog;
    Button retrybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        logo = findViewById(R.id.Applogo);


        dialog = new Dialog(this,R.style.AppTheme_ModalTheme);
        dialog.setContentView(R.layout.connection_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        retrybtn = dialog.findViewById(R.id.btnretry);

        Animation animate = AnimationUtils.loadAnimation(this,R.anim.transition);
        logo.startAnimation(animate);


        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(Intent.makeRestartActivityTask(getIntent().getComponent()));
            }
        });


        handler = new Handler();


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(connected){
                    Intent intent =  new Intent(Splashscreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else{
                    dialog.show();
                }

            }
        },1500);


    }
}