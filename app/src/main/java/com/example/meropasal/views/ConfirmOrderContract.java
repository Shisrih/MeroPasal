package com.example.meropasal.views;

public interface ConfirmOrderContract {
    interface View{
       void onSuccess();
        void onFailed(String message);
    }

    interface Presenter{
        void onSuccess();
        void onFailed(String message);
    }

}
