package com.example.meropasal.views;

import com.example.meropasal.models.products.Product;

import java.util.List;

public interface ProductContract {

    interface View{
        void getSimilarProducts(List<Product> product);
        void onSuccess(Product product, int rating);
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void getSimilarProducts(List<Product>  product);
        void onSuccess(Product product, int rating);
        void onFailed(String message);
    }
}
