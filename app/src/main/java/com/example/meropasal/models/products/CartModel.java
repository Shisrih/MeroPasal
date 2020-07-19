package com.example.meropasal.models.products;

public class CartModel {

    private int id;
    private String singleImg;
    private String productId;
    private String userid;
    private String name;

    public CartModel(int id, String userid, String productId, String name, String singleImg){
        this.id  = id;
        this.productId = productId;
        this.name = name;
        this.singleImg = singleImg;
        this.userid = userid;
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
}
