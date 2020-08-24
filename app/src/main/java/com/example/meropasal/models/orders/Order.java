package com.example.meropasal.models.orders;

import androidx.annotation.Nullable;

import com.example.meropasal.models.user.ShippingAddress;

public class Order {

    private String userid;
    private String productid;
    private float quantity;
    private float total;
    private ShippingAddress shippingAddress;
    private String paymentOption;
    private boolean paid;
    private boolean delivered;

    private boolean success;
    private String message;


    public Order(String userid, String productid, float quantity, float total,  ShippingAddress shippingAddress, String paymentOption, boolean paid, boolean delivered) {
        this.userid = userid;
        this.productid = productid;
        this.quantity = quantity;
        this.total = total;
        this.shippingAddress = shippingAddress;
        this.paymentOption = paymentOption;
        this.paid = paid;
        this.delivered = delivered;
    }

    public Order(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public String getProductid() {
        return productid;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getTotal() {
        return total;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
