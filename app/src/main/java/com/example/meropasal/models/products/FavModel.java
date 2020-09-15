package com.example.meropasal.models.products;

import java.io.Serializable;

public class FavModel implements Serializable {


    private int id;
    private String singleImg;
    private String productId;
    private String userid;
    private String name;
    private float price;


    public FavModel(int id, String userid, String productId, String name, String singleImg, float price){
        this.id  = id;
        this.productId = productId;
        this.name = name;
        this.singleImg = singleImg;
        this.userid = userid;
        this.price = price;

    }

    public FavModel(String userid, String productId){
        this.productId = productId;
        this.userid = userid;
    }

    public FavModel(String userid, String productId, String name, String singleImg, float price){

        this.productId = productId;
        this.name = name;
        this.singleImg = singleImg;
        this.userid = userid;
        this.price = price;

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



    public float getPrice() {
        return price;
    }


}
