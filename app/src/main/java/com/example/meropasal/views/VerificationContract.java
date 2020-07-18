package com.example.meropasal.views;

public interface VerificationContract {

    interface View{
        void onCodeSent(String sid);
        void onVerified();
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void onCodeSent(String sid);
        void onVerified();
        void onFailed(String message);
    }
}
