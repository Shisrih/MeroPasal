package com.example.meropasal.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.meropasal.presenters.auth.CheckEmailPresenter;
import com.example.meropasal.presenters.auth.SignupPresenter;
import com.example.meropasal.presenters.auth.VerificationPresenter;
import com.example.meropasal.utiils.Validator;
import com.example.meropasal.views.AuthContract;
import com.example.meropasal.views.CheckEmailContract;
import com.example.meropasal.views.VerificationContract;
import com.facebook.AccessToken;
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
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity implements AuthContract.View, View.OnClickListener, CheckEmailContract.View, VerificationContract.View
{
            private String sid  = "";
            private String fname, lname, address, phone, email, password, cpassword;
            private AccessToken accessToken;
            private TextView signuptxt;
            private ImageView signupclip;
            private SignupPresenter presenter;
            private CheckEmailPresenter emailpresenter;
            private VerificationPresenter verificationPresenter;
            private EditText addresstxt, fnametxt, lnametxt, phonetxt, emailtxt, passwordtxt, cpasswordtxt, vcode;
            private Button registerbtn, fbbutton, btnverify;
            private TextInputLayout passwordinp, cpasswordinp;
            private SharedPreferences sharedPreferences;
            private boolean emailAvailable = true;
            private GoogleSignInButton googlebtn;
            private LoginButton fbbtn;
            private static final String EMAIL = "email";
            private static final int RC_SIGN_IN = 1;
            //Phone Verification Dialog
             private Dialog dialog;
            private static final String TAG = "Signup";
            //googlesignin client
            GoogleSignInClient mGoogleSignInClient;

             //Facebook login callback manager
             private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signuptxt =findViewById(R.id.signuptxt);
        signupclip = findViewById(R.id.signupclip);
        viewInit();
        presenter = new SignupPresenter(this, sharedPreferences);
        Animation txt = AnimationUtils.loadAnimation(this,R.anim.zoomout);
        signuptxt.startAnimation(txt);

        Animation clip = AnimationUtils.loadAnimation(this,R.anim.blink);
        signupclip.startAnimation(clip);

        emailpresenter = new CheckEmailPresenter(this);

        verificationPresenter = new VerificationPresenter(this);

        emailtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email  = emailtxt.getText().toString();

                if(Validator.validateEmail(email)){
                    emailpresenter.checkEmailAvailability(email);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void viewInit(){
        addresstxt = findViewById(R.id.addresssign);
        fnametxt = findViewById(R.id.fname);
        lnametxt = findViewById(R.id.lname);
        phonetxt = findViewById(R.id.phonesign);
        emailtxt = findViewById(R.id.emaisign);
        passwordtxt = findViewById(R.id.passwordsign);
        cpasswordtxt = findViewById(R.id.cpasswordsign);
        registerbtn = findViewById(R.id.registerbtn);



        passwordinp = findViewById(R.id.signpassinp);
        cpasswordinp = findViewById(R.id.signcpassinp);

        fbbtn = (LoginButton) findViewById(R.id.facebooksignin);
        fbbutton = findViewById(R.id.fb);

        dialog = new Dialog(this,R.style.AppTheme_ModalTheme);
        dialog.setContentView(R.layout.phone_verification);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        vcode = dialog.findViewById(R.id.vcode);
        btnverify = dialog.findViewById(R.id.btnverify);


        googlebtn = findViewById(R.id.googlesignin);

        googlebtn.setOnClickListener(this);
        fbbtn.setOnClickListener(this);
        btnverify.setOnClickListener(this);


        //Facebook login
        facebookLogin();


        //google login
        googleLogin();
        registerbtn.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);


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

        cpasswordtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cpasswordinp.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void facebookLogin(){
        callbackManager = CallbackManager.Factory.create();

        fbbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = AccessToken.getCurrentAccessToken();
                if(accessToken == null){

                }else{
                    facebookLoadUserProfile(accessToken);
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Signup.this, "Unexpected connection error!", Toast.LENGTH_SHORT).show();
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
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }


    @Override
    public void onSuccess() {
        dialog.dismiss();
        finish();
        Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailAvailable() {

        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_tick);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        emailtxt.setError("Email is Available",customErrorDrawable);
        emailAvailable = true;
    }

    @Override
    public void onEmailUnAvailable() {
        emailAvailable = false;
        emailtxt.setError("Email Already Taken");
    }

    @Override
    public void onCodeSent(String sid) {
        this.sid = sid;
    }

    @Override
    public void onVerified() {
        vcode.setText("");
        register();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerbtn:
                authenticate();
                break;
            case R.id.googlesignin:
                signInWithGoogle();
                break;
            case R.id.fb:
                fbbtn.performClick();
                break;
            case R.id.btnverify:
                    verifyCode();
                break;
        }
    }

    private void authenticate(){
         address = addresstxt.getText().toString();
         fname = fnametxt.getText().toString();
         lname  = lnametxt.getText().toString();
         phone = phonetxt.getText().toString();
         email = emailtxt.getText().toString();
         password = passwordtxt.getText().toString();
         cpassword = cpasswordtxt.getText().toString();



        int err = 0;

        if(!Validator.validateFields(address)){
            err++;
            addresstxt.setError("Enter Valid Address");
            addresstxt.requestFocus();
        }

        if(!Validator.validateFields(fname)){
            err++;
            fnametxt.setError("Enter Valid FirstName");
            fnametxt.requestFocus();
        }

        if(!Validator.validateFields(lname)){
            err++;
            lnametxt.setError("Enter Valid LastName");
            lnametxt.requestFocus();
        }
        if(!Validator.validateFields(phone) || phone.length() < 10){
            err++;
            phonetxt.setError("Enter Valid Phone Number");
            phonetxt.requestFocus();
        }

        if(!Validator.validateEmail(email)){
            err++;
            emailtxt.setError("Enter Valid LastName");
            emailtxt.requestFocus();
        }

        if(!Validator.validateFields(password)){
            err++;
            passwordinp.setPasswordVisibilityToggleEnabled(false);
            passwordtxt.setError("Enter Valid Password");
            passwordtxt.requestFocus();
        }

        if(!Validator.validateFields(cpassword)){
            err++;
            cpasswordinp.setPasswordVisibilityToggleEnabled(false);
            cpasswordtxt.setError("Re-Enter Password");
            cpasswordtxt.requestFocus();
        }

        if(!Validator.validatePassword(password, cpassword)){
            err++;
            passwordtxt.setText("");
            cpasswordtxt.setText("");
            passwordinp.setPasswordVisibilityToggleEnabled(false);
            cpasswordinp.setPasswordVisibilityToggleEnabled(false);
            passwordtxt.setError("Password Mismatch");
            cpasswordtxt.setError("");

            passwordtxt.requestFocus();

        }
        if(!emailAvailable){
            err++;
            emailtxt.setText("");
            emailtxt.setError("Enter Unique Email!");
            emailtxt.requestFocus();
        }
        if(err == 0){
            phoneVerification();
        }


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
    private void googleLoadUserProfile(GoogleSignInAccount account){


        String personName = account.getDisplayName();
        String personGivenName = account.getGivenName();
        String personFamilyName = account.getFamilyName();
        String personEmail = account.getEmail();
        String personId = account.getId();
        Uri profile_imgURL = account.getPhotoUrl();

        register(personGivenName, personFamilyName, "", "Google", personEmail);


    }

    private void phoneVerification(){
       verificationPresenter.sendVerificationCode(phone);
        dialog.show();

    }

    private void verifyCode(){
        String code = vcode.getText().toString();
        if (!Validator.validateFields(code)) {
            vcode.setError("Enter Verification Code");
            vcode.requestFocus();
        }else{
            verificationPresenter.verifyCode(sid, phone, code);
        }
    }

    private void register(){
        presenter.register(new User(fname, lname, address, phone, email, "Account", password));
    }

    private void register(String fname, String lname, String address, String type, String email){
        presenter.register(new User(fname, lname, address, "", email, type, "password"));
    }
}