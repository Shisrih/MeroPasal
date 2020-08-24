package com.example.meropasal.views;

import com.example.meropasal.models.user.ShippingAddress;

import java.util.List;

public interface ShippingAddressContract {

    interface View{
        void onAddShippingAddress();
        void getShippingAddress(List<ShippingAddress> shippingAddress);
        void onFailed(String message);
    }

    interface Presenter{
        void onAddShippingAddress();
        void getShippingAddress(List<ShippingAddress> shippingAddress);
        void onFailed(String message);
    }
}
