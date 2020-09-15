package com.example.meropasal.presenters.search;

import com.example.meropasal.data.interactors.search.SearchInteractor;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.views.SearchContract;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private SearchInteractor interactor;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        interactor = new SearchInteractor(this);
    }

    public void getProductsBySearch(String search){
        interactor.getProductsBySearch(search);
    }

    @Override
    public void getSearchProducts(List<ProductRes> products) {
        view.getSearchProducts(products);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
