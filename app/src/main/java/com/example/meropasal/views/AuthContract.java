package com.example.meropasal.views;

/*
* MVP Android implementations are well-known to define additional
*  interfaces for views and presenters as contracts.
* The presenter only receives the interface to decouple the presenter
* from the actual given view, et vice versa.
* */
public interface AuthContract {
    //User Authentication View
    interface View{
        void onSuccess();
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void onSuccess();
        void onFailed(String message);
    }
}
