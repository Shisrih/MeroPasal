package com.example.meropasal.presenters.auth;

import com.example.meropasal.data.interactors.auth.VerificationInteractor;
import com.example.meropasal.views.VerificationContract;

public class VerificationPresenter implements VerificationContract.Presenter {

    private VerificationInteractor interactor;
    private VerificationContract.View view;

    public VerificationPresenter(VerificationContract.View view){
        this.view = view;
        interactor = new VerificationInteractor(this);
    }

    public void sendVerificationCode(String phone){
        interactor.sendVerificationCode(phone);
    }

    public void verifyCode(String sid, String phone, String code){
        interactor.verifyCode(sid,phone,code);
    }


    @Override
    public void onCodeSent(String sid) {
        view.onCodeSent(sid);
    }

    @Override
    public void onVerified() {
        view.onVerified();
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
