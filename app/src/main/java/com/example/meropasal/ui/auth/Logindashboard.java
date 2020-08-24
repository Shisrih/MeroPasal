package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.models.user.User;
import com.example.meropasal.presenters.auth.SignupPresenter;
import com.example.meropasal.views.AuthContract;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Logindashboard extends AppCompatActivity implements View.OnClickListener, AuthContract.View {
    private AccessToken accessToken;
    private LoginButton fbbtn;
    private TextView signuptxt;
    private GoogleSignInButton googlebtn;
    private Button pwdbtn, fbbutton;
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private SharedPreferences sharedPreferences;
    private SignupPresenter presenter;
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
                finish();
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

       
         presenter = new SignupPresenter( this , sharedPreferences);
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
                checkAccessToken();
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
    //Checking Facebook Login Status
    private void checkAccessToken(){
        accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken == null){

        }else{
            facebookLoadUserProfile(accessToken);
        }

        AccessTokenTracker tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){

                }else{
                    facebookLoadUserProfile(currentAccessToken);
                }
            }
        };
    }

    private void facebookLoadUserProfile(AccessToken newAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String profile_imgURL = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    String location = "";
                    try {
                        JSONObject jsonobject_location = object.getJSONObject("location");
                        location = jsonobject_location.getString("name");

                    } catch (Exception e) {
                        location = "";
                        e.printStackTrace();
                    }
                    register(first_name, last_name, location, "Facebook", email);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id, location{location}");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void googleLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleLoadUserProfile(GoogleSignInAccount account){



        String personName = account.getDisplayName();
        String personGivenName = account.getGivenName();
        String personFamilyName = account.getFamilyName();
        String personEmail = account.getEmail();
        String personId = account.getId();
        Uri profile_imgURL = account.getPhotoUrl();

        register(personGivenName, personFamilyName, "", "Google", personEmail);



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
            googleLoadUserProfile(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "handleSignInResult: " + e.toString());
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }


    @Override
    public void onSuccess() {
    finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void register(String fname, String lname, String address, String type, String email){
        presenter.register(new User(fname, lname, address, "", email, type, "password"));
    }
}