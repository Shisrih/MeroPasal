package com.example.meropasal.views;

import com.example.meropasal.models.products.Product;

public interface ProductContract {

    interface View{
        void onSuccess(Product product, int rating);
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void onSuccess(Product product, int rating);
        void onFailed(String message);
    }
}
