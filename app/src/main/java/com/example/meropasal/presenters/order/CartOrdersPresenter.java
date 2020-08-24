package com.example.meropasal.presenters.order;

import com.example.meropasal.data.interactors.order.CartOrdersInteractor;
import com.example.meropasal.models.orders.Order;
import com.example.meropasal.views.CartOrdersContract;

import java.util.List;

public class CartOrdersPresenter implements CartOrdersContract.Presenter {

    private CartOrdersContract.View view;
    private CartOrdersInteractor ordersInteractor;

    public CartOrdersPresenter(CartOrdersContract.View view) {
        this.view = view;
        ordersInteractor = new CartOrdersInteractor(this);
    }

    public void addOrders(List<Order> orders, String token){
            ordersInteractor.addOrders(orders, token);
    }

    @Override
    public void onSuccess() {
        view.onSuccess();
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
