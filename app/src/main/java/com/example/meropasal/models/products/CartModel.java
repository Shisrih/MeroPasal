package com.example.meropasal.models.products;

import java.io.Serializable;

public class CartModel implements Serializable {

    private int id;
    private String singleImg;
    private String productId;
    private String userid;
    private String name;
    private float price;
    private int quantity;
    private float totalPrice;

    public CartModel(int id, String userid, String productId, String name, String singleImg, float price, int quantity, float totalPrice){
        this.id  = id;
        this.productId = productId;
        this.name = name;
        this.singleImg = singleImg;
        this.userid = userid;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartModel(String userid, String productId, String name, String singleImg, float price, int quantity, float totalPrice){

        this.productId = productId;
        this.name = name;
        this.singleImg = singleImg;
        this.userid = userid;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }


    public String getSingleImg() {
        return singleImg;
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
