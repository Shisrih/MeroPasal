package com.example.meropasal.data.interactors.product;

import android.util.Log;

import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.models.review.RatingsBreakdown;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.API.RatingApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.views.reviews.ProductReviewsContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductReviewsInteractor {

    private ProductReviewsContract.Presenter presenter;
    private static final String TAG = "ProductReviewsInteracto";


    public ProductReviewsInteractor(ProductReviewsContract.Presenter presenter) {
        this.presenter = presenter;
    }



    public void getReviews(String productid){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<Rating> responseCall = api.getRatings(productid);

        responseCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getProductReviews(response.body().getReviews());
                    }else{
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        presenter.onFailed("Couldn't get Reviews");
                    }
                }else{
                    Log.d(TAG, "onResponse: " + response.message());
                    presenter.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews");
            }
        });
    }

    public void getReviewsByLimit(String productid){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<Rating> responseCall = api.getRatingsByLimit(productid);

        responseCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getProductReviews(response.body().getReviews());
                    }else{
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        presenter.onFailed("Couldn't get Reviews");
                    }
                }else{
                    Log.d(TAG, "onResponse: " + response.message());
                    presenter.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews");
            }
        });
    }

    public void getRatingByUser(String token, String productid){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<Rating> responseCall = api.getRatingByUser(token,productid);

        responseCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getReviewByUser(response.body().getUserReview());
                    }else{

                        presenter.onFailed("Couldn't get Reviews");
                    }
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews");
            }
        });
    }

    public void addReview(String token, Rating rating){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<Rating> responseCall = api.addRating(token,rating);

        responseCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.onAddReview();
                    }else{
                        presenter.onFailed("Couldn't Add Reviews");
                    }
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews");
            }
        });

    }


    public void getRatingsCount(String productid){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<RatingsBreakdown> responseCall = api.ratingsCount(productid);


        responseCall.enqueue(new Callback<RatingsBreakdown>() {
            @Override
            public void onResponse(Call<RatingsBreakdown> call, Response<RatingsBreakdown> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getRatingsCount(response.body().getRatingsBreakdown());
                    }else{
                        presenter.onFailed("Couldn't get Reviews Count");
                    }
                }else{
                    Log.d(TAG, "onFailure: " + response.message());
                    presenter.onFailed("Couldn't get Reviews Count");
                }
            }

            @Override
            public void onFailure(Call<RatingsBreakdown> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews Count");
            }
        });

    }
}
