package com.example.meropasal.presenters.order;

import com.example.meropasal.data.interactors.order.OrdersInteractor;
import com.example.meropasal.models.orders.OrderRes;
import com.example.meropasal.views.OrdersContract;

import java.util.List;

public class OrdersPresenter implements OrdersContract.Presenter {

    private OrdersContract.View view;
    private OrdersInteractor interactor;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
        interactor = new OrdersInteractor(this);
    }


    public void getOrdersByUser(String token){
        interactor.getOrdersByUser(token);
    }

    public void cancelOrders(String token, List<String> productid){
        interactor.calcelOrders(token, productid);
    }

    @Override
    public void ordersByUsers(List<OrderRes> orders) {
        view.ordersByUsers(orders);
    }

    @Override
    public void cancelOrders(String message) {
        view.cancelOrders(message);
    }

    @Override
    public void onFailed(String message) {
        view.onFailed(message);
    }
}
