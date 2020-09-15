package com.example.meropasal.models.orders;

import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductOrders;
import com.example.meropasal.models.user.ShippingAddress;

import java.util.List;

public class OrderRes {

    private String _id;
    private String userid;
    private ProductOrders productid;
    private float quantity;
    private float total;
    private ShippingAddress shippingAddress;
    private String paymentOption;
    private boolean paid;
    private boolean delivered;

    private boolean success;
    private String message;

    private List<OrderRes> orders;


    public OrderRes(String _id, String userid, ProductOrders productid, float quantity, float total,  ShippingAddress shippingAddress, String paymentOption, boolean paid, boolean delivered) {
        this.userid = userid;
        this.productid = productid;
        this.quantity = quantity;
        this.total = total;
        this.shippingAddress = shippingAddress;
        this.paymentOption = paymentOption;
        this.paid = paid;
        this.delivered = delivered;
        this._id = _id;
    }

    public OrderRes(boolean success, String message, List<OrderRes> orders ) {
        this.success = success;
        this.message = message;
        this.orders = orders;
    }

    public String getUserid() {
        return userid;
    }

    public ProductOrders getProductid() {
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

    public String get_id() {
        return _id;
    }

    public List<OrderRes>  getOrders() {
        return orders;
    }
}
