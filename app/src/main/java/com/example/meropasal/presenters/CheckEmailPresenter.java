package com.example.meropasal.presenters;

import com.example.meropasal.data.interactors.CheckEmailInteractor;
import com.example.meropasal.views.CheckEmailContract;

public class CheckEmailPresenter implements CheckEmailContract.Presenter {


    private CheckEmailContract.View view;
    private CheckEmailInteractor interactor;

    public CheckEmailPresenter(CheckEmailContract.View view){
        this.view = view;
        interactor = new CheckEmailInteractor(this);
    }

    public void checkEmailAvailability(String email){
        interactor.checkEmailAvailability(email);

    }


    @Override
    public void onSuccess() {
        view.onEmailAvailable();

    }

    @Override
    public void onFailed(String message) {
        view.onEmailUnAvailable();
//        view.onFailed(message);
    }
}
