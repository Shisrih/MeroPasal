package com.example.meropasal.models.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    private boolean success;
    private String message;
    private List<Product> products;

    private String _id;
    @SerializedName("product_name")
    private String name;
    private String price;
    private String detail;
    private String brand;
    private String[] image;
    private String createdAt;
    private String updatedAt;

    private List<Discount> discount;



    @SerializedName("category_id")
    private String category;

    //For Similar products
    public Product(String _id, String name, String brand, String[] image) {
        this._id = _id;
        this.name = name;
        this.brand = brand;
        this.image = image;
    }

    public Product(boolean success, String message, List<Product> products) {
        this.success = success;
        this.message = message;
        this.products = products;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product(String _id, String name, String price, String detail, String brand, String[] image, String category, String createdAt, String updatedAt) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.brand = brand;
        this.image = image;

        this.category = category;
    }

    public Product(String _id, String name, String price, String detail, String brand, String[] image, String category, String createdAt, String updatedAt, List<Discount> discount) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.brand = brand;
        this.image = image;
        this.discount = discount;
        this.category = category;
    }





    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public String getBrand() {
        return brand;
    }

    public String[] getImage() {
        return image;
    }


    public String getCreatedAt() {
        return createdAt;
    }



    public String getUpdatedAt() {
        return updatedAt;
    }


    public List<Discount> getDiscount() {
        return discount;
    }

    public String getCategory() {
        return category;
    }
}
