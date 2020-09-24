package com.example.meropasal.ui.acount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.models.user.User;
import com.example.meropasal.presenters.user.ProfilePresenter;
import com.example.meropasal.presenters.user.UpdatePresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Validator;
import com.example.meropasal.views.ProfileContract;
import com.example.meropasal.views.UpdateContract;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserInfo extends AppCompatActivity implements ProfileContract.View, UpdateContract.View {

    private SharedPreferences sharedPreferences;
    private EditText fnametxt, lnametxt, addresstxt, phonetxt;
    private Button editbtn;
    private TextView fullnametxt;
    private CircleImageView profileimg;
    private String profile_img, token;
        private ProfilePresenter profilePresenter;
        private UpdatePresenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        profile_img = sharedPreferences.getString(Constants.PROFILE_PIC, null);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        checkAuth();
        viewInit();





    }


    private void viewInit(){
        fullnametxt = findViewById(R.id.fullname);
        fnametxt = findViewById(R.id.fname);
        lnametxt = findViewById(R.id.lname);
        addresstxt = findViewById(R.id.address);
        phonetxt = findViewById(R.id.phone);

        profileimg = findViewById(R.id.profileimg);
        editbtn = findViewById(R.id.editbtn);



        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });


        profilePresenter = new ProfilePresenter(this);

        presenter = new UpdatePresenter(this);

        profilePresenter.getProfile(token);


    }


    private void validateForm(){
    String fname = fnametxt.getText().toString();
    String lname = lnametxt.getText().toString();
    String address = addresstxt.getText().toString();
    String phone = phonetxt.getText().toString();



    int err = 0;

    if(!Validator.validateFields(fname)){
        err++;
    }

    if(!Validator.validateFields(lname)){
        err++;
    }
        if(!Validator.validateFields(address)){
            err++;
        }

        if(!Validator.validateFields(phone)){
            err++;
        }


        if(err==0){
            editUser(fname,lname,address,phone);
        }


    }



    private void editUser(String fname, String lname, String address, String phone){
        presenter.updateUser(token, new User(fname, lname, address, phone));
    }

    private void checkAuth(){
        if(!Authenticator.checkLoginStatus(sharedPreferences)){
            redirect();
        }
    }


    private void redirect(){
        finish();
        startActivity(new Intent(this, Logindashboard.class));
    }


    @Override
    public void getUser(User user) {
                fullnametxt.setText(user.getFirstname() + " " + user.getLastname());
                fnametxt.setText(user.getFirstname());
                lnametxt.setText(user.getLastname());
                addresstxt.setText(user.getLocation());
                phonetxt.setText(user.getPhone());


        Picasso.get().load(profile_img).into(profileimg);

    }

    @Override
    public void onUpdateUser(User user) {
        View parentLayout = findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar
                .make(parentLayout, "User Info Updated!", Snackbar.LENGTH_LONG);
        snackbar.show();

        fullnametxt.setText(user.getFirstname() + " " + user.getLastname());
        fnametxt.setText(user.getFirstname());
        lnametxt.setText(user.getLastname());
        addresstxt.setText(user.getLocation());
        phonetxt.setText(user.getPhone());
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}