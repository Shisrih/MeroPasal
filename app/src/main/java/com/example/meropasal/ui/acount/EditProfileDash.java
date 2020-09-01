package com.example.meropasal.ui.acount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meropasal.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileDash extends AppCompatActivity {

    private LinearLayout editprofile,editlogincred;
    private CircleImageView imageview;
    private TextView fullnametxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editprofile = findViewById(R.id.edituserinfo);
        editlogincred = findViewById(R.id.editlogincredentials);
        imageview = findViewById(R.id.profile);
        fullnametxt = findViewById(R.id.fullname);

        String profileimg = getIntent().getStringExtra("image");
        String fname = getIntent().getStringExtra("fname");
        String lname = getIntent().getStringExtra("lname");



        if(profileimg == ""){
            imageview.setImageResource(R.drawable.fox);
        }else{
            Picasso.get().load(profileimg).into(imageview);
        }
        String fullname = fname + " " + lname;

        fullnametxt.setText(fullname);

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