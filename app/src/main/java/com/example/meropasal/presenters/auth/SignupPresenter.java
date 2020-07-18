package com.example.meropasal.presenters.auth;

import android.content.SharedPreferences;

import com.example.meropasal.data.interactors.auth.SignupInteractor;
import com.example.meropasal.models.user.User;
import com.example.meropasal.views.AuthContract;

public class SignupPresenter implements AuthContract.Presenter {


    private AuthContract.View view;
    private SignupInteractor interactor;

    public SignupPresenter(AuthContract.View view, SharedPreferences sharedPreferences){
        this.view = view;

        interactor = new SignupInteractor(this, sharedPreferences);
    }

    public void register(User user){
        interactor.signup(user);
    }

    @Override
    public void onSuccess() {
        view.onSuccess();
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
