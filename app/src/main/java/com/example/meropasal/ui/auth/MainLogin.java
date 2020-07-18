package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.models.user.User;
import com.example.meropasal.presenters.auth.LoginPresenter;
import com.example.meropasal.utiils.Validator;
import com.example.meropasal.views.AuthContract;
import com.google.android.material.textfield.TextInputLayout;

public class MainLogin extends AppCompatActivity implements AuthContract.View, View.OnClickListener {

            private LoginPresenter presenter;
            private EditText emailtxt, passwordtxt;
            private Button lgnbtn;
            private TextView logintxt;
            private ImageView clipart;
            private TextInputLayout passwordinp;
            private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        viewInit();

        presenter = new LoginPresenter(this, sharedPreferences);

        Animation logintext = AnimationUtils.loadAnimation(this,R.anim.zoomout);
        logintxt.startAnimation(logintext);

        Animation loginclipart = AnimationUtils.loadAnimation(this,R.anim.blink);
        clipart.startAnimation(loginclipart);




        passwordtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordinp.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void viewInit(){
        logintxt = findViewById(R.id.mainlogintxt);
        clipart = findViewById(R.id.mainloginclip);
        emailtxt = findViewById(R.id.lgnemail);
        passwordtxt = findViewById(R.id.lgnpassword);
        lgnbtn = findViewById(R.id.lgnbtn);
        passwordinp = findViewById(R.id.passwordinp);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        lgnbtn.setOnClickListener(this);

    }
    @Override
    public void onSuccess() {
        finish();
        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String message) {
        emailtxt.setError(message);
        emailtxt.setText("");
        passwordtxt.setText("");
        emailtxt.requestFocus();
        passwordinp.setPasswordVisibilityToggleEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.lgnbtn:
                String email = emailtxt.getText().toString();
                String password = passwordtxt.getText().toString();
                authentication(email, password);

                break;
        }
    }


    private void authentication(String email, String password){
        int err = 0;
        if(!Validator.validateEmail(email)){
            err++;
            emailtxt.setError("Enter Valid Email");
            emailtxt.requestFocus();
        }
        if(!Validator.validateFields(password)){
            err++;
            passwordinp.setPasswordVisibilityToggleEnabled(false);
            passwordtxt.setError("Enter Valid Password");
            passwordtxt.requestFocus();

        }

        if(err == 0){
            login(email, password);
        }
    }

    private void login(String email, String password){
            presenter.start(new User(email, password));
    }


}