package com.example.meropasal.views;

import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.user.User;

import java.util.List;

public interface ProductContract {

    interface View{
        void getExclusiveProducts(List<Product> products);
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void getExclusiveProducts(List<Product> products);
        void onFailed(String message);
    }
}
