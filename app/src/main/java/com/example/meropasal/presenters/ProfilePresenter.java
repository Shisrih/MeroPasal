package com.example.meropasal.presenters;

import com.example.meropasal.data.interactors.ProfileInteractor;
import com.example.meropasal.models.User;
import com.example.meropasal.views.ProfileContract;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileInteractor interactor;
    private ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view){
        this.view = view;

        interactor = new ProfileInteractor(this);
    }

    public void getProfile(String token){
        interactor.getUser(token);
    }

    @Override
    public void getUser(User user) {
        view.getUser(user);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
