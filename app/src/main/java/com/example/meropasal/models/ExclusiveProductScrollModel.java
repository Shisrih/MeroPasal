package com.example.meropasal.models;

public class ExclusiveProductScrollModel {
    private int exlusiveproductimg;
    private String exclusiveproductname;
    private String exclusiveprice;
    private String previousprice;

    public ExclusiveProductScrollModel(int exlusiveproductimg, String exclusiveproductname, String exclusiveprice, String previousprice) {
        this.exlusiveproductimg = exlusiveproductimg;
        this.exclusiveproductname = exclusiveproductname;
        this.exclusiveprice = exclusiveprice;
        this.previousprice = previousprice;
    }

    public int getExlusiveproductimg() {
        return exlusiveproductimg;
    }

    public void setExlusiveproductimg(int exlusiveproductimg) {
        this.exlusiveproductimg = exlusiveproductimg;
    }

    public String getExclusiveproductname() {
        return exclusiveproductname;
    }

    public void setExclusiveproductname(String exclusiveproductname) {
        this.exclusiveproductname = exclusiveproductname;
    }

    public String getExclusiveprice() {
        return exclusiveprice;
    }

    public void setExclusiveprice(String exclusiveprice) {
        this.exclusiveprice = exclusiveprice;
    }

    public String getPreviousprice() {
        return previousprice;
    }

    public void setPreviousprice(String previousprice) {
        this.previousprice = previousprice;
    }
}
