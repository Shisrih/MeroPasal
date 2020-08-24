package com.example.meropasal.presenters.order;

import com.example.meropasal.data.interactors.order.ConfirmOrderInteractor;
import com.example.meropasal.models.orders.Order;
import com.example.meropasal.views.ConfirmOrderContract;

public class ConfirmOrderPresenter implements ConfirmOrderContract.Presenter {

    private ConfirmOrderContract.View view;
    private ConfirmOrderInteractor interactor;

    public ConfirmOrderPresenter(ConfirmOrderContract.View view) {
        this.view = view;
        this.interactor = new ConfirmOrderInteractor(this);
    }

    public void addOrder( String token, Order order){
            interactor.addOrder(token, order);
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
