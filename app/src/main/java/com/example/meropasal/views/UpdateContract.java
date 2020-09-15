package com.example.meropasal.views;

import com.example.meropasal.models.user.User;

public interface UpdateContract {

    interface View{
        void onUpdateUser(User user);
        void onFailed(String message);
    }


    interface Presenter{
        void onUpdateUser(User user);
        void onFailed(String message);
    }
}
