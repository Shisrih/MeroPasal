package com.example.meropasal.models.review;

import com.example.meropasal.models.user.User;

import java.util.Date;
import java.util.List;

public class Rating {

    private String _id;
    private User user;
    private String product;
    private float ratings;
    private String review;
    private List<Rating> reviews;
    private String message;
    private boolean success;
    private Date createdAt;
    private Date updatedAt;

    private Rating userReview;


    public Rating(String _id, User user, String product, float ratings, String review, Date createdAt, Date updatedAt) {
        this.user = user;
        this.product = product;
        this.ratings = ratings;
        this.review = review;
        this._id = _id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Rating(boolean success, String message){
        this.success  = success;
        this.message = message;
    }

    public Rating(String product, float ratings, String review){
        this.product = product;
        this.ratings = ratings;
        this.review = review;
    }

    public Rating(  boolean success, String message, List<Rating> reviews) {
        this.reviews = reviews;
        this.message = message;
        this.success = success;
    }
    public Rating(  boolean success, String message, Rating userReview) {
        this.userReview = userReview;
        this.message = message;
        this.success = success;
    }
    
    public User getUser() {
        return user;
    }

    public String getProduct() {
        return product;
    }

    public float getRatings() {
        return ratings;
    }

    public String getReview() {
        return review;
    }

    public String get_id() {
        return _id;
    }


    public List<Rating> getReviews() {
        return reviews;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Rating getUserReview() {
        return userReview;
    }
}
