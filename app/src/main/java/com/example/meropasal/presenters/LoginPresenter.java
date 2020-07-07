package com.example.meropasal.presenters;

import android.content.SharedPreferences;

import com.example.meropasal.data.interactors.LoginInteractor;
import com.example.meropasal.models.User;
import com.example.meropasal.views.AuthContract;

public class LoginPresenter implements AuthContract.Presenter {

    private AuthContract.View view;
    private LoginInteractor loginInteractor;


    public LoginPresenter(AuthContract.View view, SharedPreferences sharedPreferences){
        this.view = view;

        loginInteractor  = new LoginInteractor(this, sharedPreferences);
    }


    public void start(User user){
        loginInteractor.login(user);
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
