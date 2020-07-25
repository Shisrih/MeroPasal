package com.example.meropasal.presenters.home;

import com.example.meropasal.data.interactors.home.HomeInteractor;
import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.views.HomeContract;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private HomeInteractor interactor;
    private HomeContract.View view;


    public HomePresenter(HomeContract.View view){
        this.view = view;
        interactor = new HomeInteractor(this);
    }

    public void getExclusiveProducts(){
        interactor.getExclusiveProducts();
    }

    public void getLatestProducts(){
        interactor.getLatestProducts();
    }

    public void getCategories(String limit){
        interactor.getCategories(limit);
    }

    @Override
    public void getLatestProducts(List<ProductRes> products) {
        view.getLatestProducts(products);
    }

    @Override
    public void getCategories(List<Category> categories) {
        view.getCategories(categories);
    }

    @Override
    public void getExclusiveProducts(List<Product> products) {
        view.getExclusiveProducts(products);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
