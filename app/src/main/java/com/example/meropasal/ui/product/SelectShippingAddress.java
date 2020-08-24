package com.example.meropasal.ui.product;

import androidx.annotation.Nullable;

import com.example.meropasal.models.user.ShippingAddress;

public interface SelectShippingAddress {

     void selectedShippingAddress(@Nullable  ShippingAddress shippingAddress);
    void isShippingAddressSelected(boolean isSelected);
}
