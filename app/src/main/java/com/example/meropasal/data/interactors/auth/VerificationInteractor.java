package com.example.meropasal.data.interactors.auth;

import android.util.Log;

import com.example.meropasal.models.user.VerificationResponse;
import com.example.meropasal.network.API.AuthApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.auth.VerificationPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationInteractor {

    private VerificationPresenter presenter;
    private static final String TAG = "VerificationInteractor";

    public VerificationInteractor(VerificationPresenter presenter){
        this.presenter = presenter;
    }

    public void sendVerificationCode(final String phone){
        AuthApi api = RetrofitIniti.initialize().create(AuthApi.class);
        Call<VerificationResponse> responseCall = api.sendVerificationCode(phone);

        responseCall.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if(response.isSuccessful()){
                    presenter.onCodeSent(response.body().getSId());


                }else{
                    presenter.onFailed(" Verification Code Not Sent!");
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Connection error");
            }
        });
    }


    public void verifyCode(String sid, String phone, String code){
        AuthApi api = RetrofitIniti.initialize().create(AuthApi.class);
        Call<VerificationResponse> responseCall = api.verifyCode(sid,phone,code);

        responseCall.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if(response.isSuccessful()){
                    presenter.onVerified();
                }else{
                    presenter.onFailed("Invalid Code!");
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Connection error");
            }
        });

    }
}
