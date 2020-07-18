package com.example.meropasal.views;

import com.example.meropasal.models.user.User;

public interface ProfileContract {
    interface View{
        void getUser(User user);
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void getUser(User user);
        void onFailed(String message);
    }
}
