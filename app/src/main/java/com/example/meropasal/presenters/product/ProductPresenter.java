package com.example.meropasal.presenters.product;

import com.example.meropasal.data.interactors.product.ProductInteractor;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.views.ProductContract;

public class ProductPresenter implements ProductContract.Presenter {

    private ProductContract.View view;
    private ProductInteractor interactor;


    public ProductPresenter(ProductContract.View view) {
        this.view = view;
        interactor  = new ProductInteractor(this);
    }

    public void getProductById(String id){
        interactor.getProductById(id);
    }

    @Override
    public void onSuccess(Product product, int rating) {
        view.onSuccess(product, rating);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
