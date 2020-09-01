package com.example.meropasal.presenters.product;

import android.util.Log;

import com.example.meropasal.data.interactors.product.ProductReviewsInteractor;
import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.review.RatingsBreakdown;
import com.example.meropasal.views.reviews.ProductReviewsContract;

import java.util.List;

public class ProductReviewsPresenter implements ProductReviewsContract.Presenter
{

    private ProductReviewsContract.View view;
    private ProductReviewsInteractor interactor;

    public ProductReviewsPresenter(ProductReviewsContract.View view) {
        this.view = view;
        interactor = new ProductReviewsInteractor(this);
    }

    public void getReviews(String productid){
        interactor.getReviews(productid);

    }
    public void getReviewsByLimit(String productid){
        interactor.getReviewsByLimit(productid);
    }

    public void addReview(String token, Rating rating){
        interactor.addReview(token, rating);
    }

    public void getRatingByUser(String token, String productid){
        interactor.getRatingByUser(token, productid);
    }
    public void getRatingsCount(String productid){
        interactor.getRatingsCount(productid);
    }

    @Override
    public void getProductReviews(List<Rating> ratingList) {
        view.getProductReviews(ratingList);
    }

    @Override
    public void getReviewByUser(Rating rating) {
        view.getReviewByUser(rating);
    }

    @Override
    public void getRatingsCount(List<RatingsBreakdown> ratingsBreakdownList) {
        view.getRatingsCount(ratingsBreakdownList);
    }

    @Override
    public void onAddReview() {
        view.onAddReview();
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
