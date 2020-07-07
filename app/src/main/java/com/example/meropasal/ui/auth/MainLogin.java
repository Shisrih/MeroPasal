package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.models.User;
import com.example.meropasal.presenters.LoginPresenter;
import com.example.meropasal.views.AuthContract;

public class MainLogin extends AppCompatActivity implements AuthContract.View, View.OnClickListener {

            private LoginPresenter presenter;
            private EditText emailtxt, passwordtxt;
            private Button lgnbtn;
            private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        viewInit();

        presenter = new LoginPresenter(this, sharedPreferences);



    }

    private void viewInit(){
        emailtxt = findViewById(R.id.lgnemail);
        passwordtxt = findViewById(R.id.lgnpassword);
        lgnbtn = findViewById(R.id.lgnbtn);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        lgnbtn.setOnClickListener(this);

    }
    @Override
    public void onSuccess() {
        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.lgnbtn:
                String email = emailtxt.getText().toString();
                String password = passwordtxt.getText().toString();
                login(email, password);

                break;
        }
    }


    private void login(String email, String password){
            presenter.start(new User(email, password));
    }
}