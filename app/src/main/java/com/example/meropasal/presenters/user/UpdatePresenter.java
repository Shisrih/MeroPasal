package com.example.meropasal.presenters.user;

import com.example.meropasal.data.interactors.user.UpdateInteractor;
import com.example.meropasal.models.user.User;
import com.example.meropasal.views.UpdateContract;

public class UpdatePresenter implements UpdateContract.Presenter {

    private UpdateContract.View view;
    private UpdateInteractor interactor;

    public UpdatePresenter(UpdateContract.View view) {
        this.view = view;
        interactor = new UpdateInteractor(this);
    }


    public void updateUser(String token, User user){
        interactor.updateUser(token, user);
    }


    @Override
    public void onUpdateUser(User user) {
        view.onUpdateUser(user);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
