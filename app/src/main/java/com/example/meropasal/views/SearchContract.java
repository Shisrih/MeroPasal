package com.example.meropasal.views;

import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.models.review.Rating;

import java.util.List;

public interface SearchContract {

    interface View{
        void getSearchProducts(List<ProductRes> products);

        void onFailed(String message);
    }


    //User Authentication Presenter
    interface Presenter{
        void getSearchProducts(List<ProductRes> products);

        void onFailed(String message);
    }
}
