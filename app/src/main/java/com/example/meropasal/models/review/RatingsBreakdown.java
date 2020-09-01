package com.example.meropasal.models.review;

import java.util.List;

public class RatingsBreakdown {

    private boolean success;
    private String message;

    private int rating;
    private int count;
    private List<RatingsBreakdown> ratingsBreakdown;

    public RatingsBreakdown(boolean success, String message, List<RatingsBreakdown> ratingsBreakdown) {
        this.success = success;
        this.message = message;
        this.ratingsBreakdown = ratingsBreakdown;
    }

    public RatingsBreakdown(int rating, int count) {
        this.rating = rating;
        this.count = count;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getRating() {
        return rating;
    }

    public int getCount() {
        return count;
    }

    public List<RatingsBreakdown> getRatingsBreakdown() {
        return ratingsBreakdown;
    }
}
