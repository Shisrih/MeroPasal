package com.example.meropasal.views.reviews;

import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.review.RatingsBreakdown;

import java.util.List;

public interface ProductReviewsContract {

    interface View{
        void getProductReviews(List<Rating> ratingList);
        void getReviewByUser(Rating rating);
        void getRatingsCount(List<RatingsBreakdown> ratingsBreakdownList);
        void onAddReview();
        void onFailed(String message);
    }


    interface Presenter{
        void getProductReviews(List<Rating> ratingList);
        void getReviewByUser(Rating rating);
        void getRatingsCount(List<RatingsBreakdown> ratingsBreakdownList);
        void onAddReview();
        void onFailed(String message);
    }
}
