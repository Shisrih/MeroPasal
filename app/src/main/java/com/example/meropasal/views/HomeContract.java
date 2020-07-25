package com.example.meropasal.views;

import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.models.user.User;

import java.util.List;

public interface HomeContract {

    interface View{
        void getLatestProducts(List<ProductRes> products);
        void getCategories(List<Category> categories);
        void getExclusiveProducts(List<Product> products);
        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void getLatestProducts(List<ProductRes> products);
        void getCategories(List<Category> categories);
        void getExclusiveProducts(List<Product> products);
        void onFailed(String message);
    }
}
