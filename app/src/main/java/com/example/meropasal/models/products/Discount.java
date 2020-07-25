package com.example.meropasal.models.products;

import com.google.gson.annotations.SerializedName;

public class Discount {

    private String _id;
    @SerializedName("discount_name")
    private String discountName;

    @SerializedName("discount_value")
    private String discountValue;


    public Discount(String _id, String discountName, String discountValue) {
        this._id = _id;
        this.discountName = discountName;
        this.discountValue = discountValue;
    }


    public String get_id() {
        return _id;
    }

    public String getDiscountName() {
        return discountName;
    }

    public String getDiscountValue() {
        return discountValue;
    }
}
