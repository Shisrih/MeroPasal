package com.example.meropasal.presenters.user;

import com.example.meropasal.data.interactors.user.ShippingAddressInteractor;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.views.ShippingAddressContract;

import java.util.List;

public class ShippingAddressPresenter implements ShippingAddressContract.Presenter {

    private ShippingAddressContract.View view;
    private ShippingAddressInteractor interactor;

    public ShippingAddressPresenter(ShippingAddressContract.View view){
        this.view = view;
        interactor = new ShippingAddressInteractor(this);
    }

    public void addShippingAddress(ShippingAddress shippingAddress, String token){
        interactor.addShippingAddress(shippingAddress, token);
    }

    public void getShippingAddress(String token){
        interactor.getShippingAddress(token);
    }



    @Override
    public void onAddShippingAddress() {
        view.onAddShippingAddress();
    }

    @Override
    public void getShippingAddress(List<ShippingAddress> shippingAddress) {
        view.getShippingAddress(shippingAddress);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
