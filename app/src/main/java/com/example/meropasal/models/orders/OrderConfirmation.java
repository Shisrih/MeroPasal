package com.example.meropasal.models.orders;

import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.user.ShippingAddress;

import java.io.Serializable;
import java.util.List;

public class OrderConfirmation implements Serializable {

    private ShippingAddress shippingAddress;
    private CartModel cart;
    private List<CartModel> cartList;
    private float shippingCharge;
    private float subTotal;


    public OrderConfirmation(ShippingAddress shippingAddress, CartModel cart, float shippingCharge, float subTotal) {
        this.shippingAddress = shippingAddress;
        this.cart = cart;
        this.shippingCharge = shippingCharge;
        this.subTotal = subTotal;
    }

    public OrderConfirmation(ShippingAddress shippingAddress, List<CartModel> cartList, float shippingCharge, float subTotal) {
        this.shippingAddress = shippingAddress;
        this.cartList = cartList;
        this.shippingCharge = shippingCharge;
        this.subTotal = subTotal;
    }
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public CartModel getCart() {
        return cart;
    }

    public float getShippingCharge() {
        return shippingCharge;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public List<CartModel> getCartList() {
        return cartList;
    }
}
