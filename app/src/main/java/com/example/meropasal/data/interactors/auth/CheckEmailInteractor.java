package com.example.meropasal.data.interactors.auth;

import android.util.Log;

import com.example.meropasal.models.user.User;
import com.example.meropasal.network.API.AuthApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.auth.CheckEmailPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckEmailInteractor {

    private CheckEmailPresenter presenter;
    private static final String TAG = "CheckEmailInteractor";


    public CheckEmailInteractor(CheckEmailPresenter presenter){
        this.presenter = presenter;
    }

    public void checkEmailAvailability(String email){
        AuthApi api = RetrofitIniti.initialize().create(AuthApi.class);
        Call<User> emailresponse = api.checkEmail(email);

        emailresponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    if(response.body().isSuccess()){
                        presenter.onSuccess();
                    }else{
                        presenter.onFailed("Email Not Availaable");

                    }

                }else{
                    presenter.onFailed("Email Not Availaable");

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

}
