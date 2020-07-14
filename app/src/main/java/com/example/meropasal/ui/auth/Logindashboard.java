package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.utiils.Constants;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Logindashboard extends AppCompatActivity implements View.OnClickListener {

    private LoginButton fbbtn;
    private TextView signuptxt;
    private GoogleSignInButton googlebtn;
    private Button pwdbtn, fbbutton;
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private SharedPreferences sharedPreferences;

    private static final String TAG = "Logindashboard";


    //googlesignin client
    GoogleSignInClient mGoogleSignInClient;

    //Facebook login callback manager
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindashboard);
        pwdbtn = findViewById(R.id.passwordsignin);
        signuptxt = findViewById(R.id.Signuptxt);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        pwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logindashboard.this, MainLogin.class);
                startActivity(intent);
            }
        });

        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logindashboard.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });


        fbbtn = (LoginButton) findViewById(R.id.facebooksignin);
        fbbutton = findViewById(R.id.fb);


        googlebtn = findViewById(R.id.googlesignin);

        googlebtn.setOnClickListener(this);
        fbbtn.setOnClickListener(this);


        //Facebook login
        facebookLogin();


        //google login
       googleLogin();

    }

    private void facebookLogin(){
        fbbtn.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();

        fbbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Logindashboard.this, "Unexpected connection error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: " + error.toString());
            }
        });



    }

    private void googleLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }







    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.googlesignin:
                signInWithGoogle();
                break;
            case R.id.fb:
                fbbtn.performClick();
                break;
        }
    }

    private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "handleSignInResult: " + e.toString());
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }


}