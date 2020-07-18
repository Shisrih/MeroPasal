package com.example.meropasal.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meropasal.R;
import com.example.meropasal.models.user.User;
import com.example.meropasal.presenters.user.ProfilePresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.views.ProfileContract;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class Account extends Fragment implements ProfileContract.View {
        private Button registerbtn, googlelogout, accountlogout;
        private AccessToken accessToken;
        private CircleImageView profileimg;
        private TextView fullname,acclink;
        private LoginButton fblgn;
        private GoogleSignInClient mGoogleSignInClient;
        private ProfilePresenter profilePresenter;
        private SharedPreferences sharedPreferences;
        private final int FACEBOOK = 1;
        private final int GOOGLE = 2;
        private final int ACCOUNT = 3;
    private static final String TAG = "Account";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_account, container, false);
        //Initializing components
         viewInit(root);






        return root;
    }
    //initializing view Components
    private void viewInit(View root){
        profileimg = root.findViewById(R.id.userimg);
        registerbtn = root.findViewById(R.id.Loginbtn);
        fblgn = root.findViewById(R.id.facebooksignin);
        fullname = root.findViewById(R.id.fullname);
        accountlogout = root.findViewById(R.id.accountlogout);
        googlelogout = root.findViewById(R.id.googlelogout);
        acclink = root.findViewById(R.id.acclink);

        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Logindashboard.class);
                startActivity(intent);
            }
        });

        accountlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor  = sharedPreferences.edit();
                editor.remove(Constants.TOKEN);
                editor.remove(Constants.ACCOUNT);

                editor.commit();
                unLoadUserProfile();
            }
        });

        profilePresenter = new ProfilePresenter(this);



        googlelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             signOut();
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
                    unLoadUserProfile();
                }else{
                    facebookLoadUserProfile(currentAccessToken);
                }
            }
        };
    }
    //Checking Google Login
    private void checkGoogleLogin(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(account == null){

        }else{
            googleLoadUserProfile(account);
        }
    }

    private void googleLoadUserProfile(GoogleSignInAccount account){
            updateUI(GOOGLE);


            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri profile_imgURL = account.getPhotoUrl();


            fullname.setText(personName);
            Picasso.get().load(profile_imgURL).into(profileimg);


    }

    //updating UI according to logged in account
    private void updateUI(int account){

        if(account == FACEBOOK){
            registerbtn.setVisibility(View.GONE);
            fblgn.setVisibility(View.VISIBLE);
            accountlogout.setVisibility(View.GONE);
            fullname.setVisibility(View.VISIBLE);
            acclink.setVisibility(View.VISIBLE);
        }else if(account == GOOGLE){
            registerbtn.setVisibility(View.GONE);
            googlelogout.setVisibility(View.VISIBLE);
            accountlogout.setVisibility(View.GONE);
            fullname.setVisibility(View.VISIBLE);
            acclink.setVisibility(View.VISIBLE);
        }else if(account == ACCOUNT){
            registerbtn.setVisibility(View.GONE);
            accountlogout.setVisibility(View.VISIBLE);
            fullname.setVisibility(View.VISIBLE);
            acclink.setVisibility(View.VISIBLE);
        }



    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        unLoadUserProfile();
                    }
                });
    }

    //facebook login success
    private void facebookLoadUserProfile(AccessToken newAccessToken){

        updateUI(FACEBOOK);


        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String profile_imgURL = "https://graph.facebook.com/" + id + "/picture?type=normal";


                    Picasso.get().load(profile_imgURL).into(profileimg);

                    fullname.setText(first_name + " " + last_name);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    //Checking App Account Login
    private void checkAccountLogin(){
        String token = sharedPreferences.getString(Constants.TOKEN, null);
        String account = sharedPreferences.getString(Constants.ACCOUNT, null);
        Log.d(TAG, "checkAccountLogin: " + account);
        if(token != null  && account != null ){
                profilePresenter.getProfile(token);

        }else{

        }
    }

    //Unloading UI to default
    private void unLoadUserProfile(){
        fblgn.setVisibility(View.GONE);
        accountlogout.setVisibility(View.GONE);
        googlelogout.setVisibility(View.GONE);
        fullname.setVisibility(View.GONE);
        acclink.setVisibility(View.GONE);
        registerbtn.setVisibility(View.VISIBLE);
        profileimg.setImageResource(R.drawable.fox);
    }

    @Override
    public void onStart() {
        checkAccessToken();
        checkGoogleLogin();
        checkAccountLogin();

        super.onStart();
    }

    @Override
    public void getUser(User user) {

        updateUI(ACCOUNT);

        fullname.setText(user.getFirstname() + " " + user.getLastname());

    }

    @Override
    public void onFailed(String message) {
        unLoadUserProfile();
    }
}