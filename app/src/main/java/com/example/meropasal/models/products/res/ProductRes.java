package com.example.meropasal.models.products.res;

import com.example.meropasal.models.products.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductRes {

    private  boolean success;
    private String message;
    private List<ProductRes> products;



    private Product product;

    @SerializedName("avgratings")
    private int avgRatings;


    public ProductRes(boolean success, String message, List<ProductRes> products) {
        this.success = success;
        this.message = message;
        this.products = products;
    }


    public ProductRes(Product product, int avgRatings) {
        this.product = product;
        this.avgRatings = avgRatings;
    }

    public ProductRes(boolean success, String message, Product product, int avgRatings) {
        this.success = success;
        this.message = message;
        this.product = product;
        this.avgRatings = avgRatings;
    }



    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ProductRes> getProducts() {
        return products;
    }

    public Product getProduct() {
        return product;
    }

    public int getAvgRatings() {
        return avgRatings;
    }
}
