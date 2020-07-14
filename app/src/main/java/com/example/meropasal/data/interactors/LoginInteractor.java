package com.example.meropasal.data.interactors;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.meropasal.models.User;
import com.example.meropasal.network.API.AuthApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.views.AuthContract;
import com.facebook.share.Share;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/*
* Interactor class helps communication with the model*/
public class LoginInteractor {

    private AuthContract.Presenter presenter;
    private static final String TAG = "LoginInteractor";

    public SharedPreferences sharedPreferences;


    public LoginInteractor(AuthContract.Presenter presenter, SharedPreferences sharedPreferences){
        this.presenter = presenter;
        this.sharedPreferences = sharedPreferences;
    }



    public void login(User user){
        AuthApi api = RetrofitIniti.initialize().create(AuthApi.class);
        Call<User>  loginresponse = api.login(user);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        loginresponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: " + response.message());
                if(response.isSuccessful()){

                    editor.putString(Constants.TOKEN, response.body().getToken());

                    editor.commit();
                    authentication(true);
                }else{

                    authentication(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Connection Failure!");
            }
        });

    }
    private void authentication(boolean valid){
        if(valid){
            presenter.onSuccess();
        }else{
            presenter.onFailed("Invalid email or password");
        }


    }
}
