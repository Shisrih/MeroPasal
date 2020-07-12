package com.example.meropasal.views;

public interface CheckEmailContract {

    interface View{
        void onEmailAvailable();
        void onEmailUnAvailable();
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void onSuccess();
        void onFailed(String message);
    }
}
