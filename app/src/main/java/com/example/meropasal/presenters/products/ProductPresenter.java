package com.example.meropasal.presenters.products;

import com.example.meropasal.data.interactors.products.ProductInteractor;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.views.ProductContract;

import java.util.List;

public class ProductPresenter implements ProductContract.Presenter {

    private ProductInteractor interactor;
    private ProductContract.View view;


    public ProductPresenter(ProductContract.View view){
        this.view = view;
        interactor = new ProductInteractor(this);
    }

    public void getExclusiveProducts(){
        interactor.getExclusiveProducts();
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
