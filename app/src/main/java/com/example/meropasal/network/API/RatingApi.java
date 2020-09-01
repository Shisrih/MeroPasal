package com.example.meropasal.network.API;

import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.review.RatingsBreakdown;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingApi {



    @GET("ratings/{product}")
    Call<Rating> getRatings(@Path("product") String product);

    @GET("ratings/limit/{product}")
    Call<Rating> getRatingsByLimit(@Path("product") String product);

    @GET("ratings/user/{product}")
    Call<Rating> getRatingByUser(@Header("authorization") String token,
            @Path("product") String product);


    @POST("ratings")
    Call<Rating> addRating(@Header("authorization") String token,
                           @Body Rating rating);

    @GET("ratings/count/{product}")
    Call<RatingsBreakdown> ratingsCount(@Path("product") String product);
}
