package com.example.meropasal.data.interactors.order;

import android.util.Log;

import com.example.meropasal.models.orders.Order;
import com.example.meropasal.models.orders.OrderRes;
import com.example.meropasal.network.API.OrderApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.views.OrdersContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersInteractor {

    private OrdersContract.Presenter presenter;
    private static final String TAG = "OrdersInteractor";

    public OrdersInteractor(OrdersContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public void getOrdersByUser(String token){
        OrderApi api = RetrofitIniti.initialize().create(OrderApi.class);
        Call<OrderRes> responseCall = api.getOrdersByUser(token);

        responseCall.enqueue(new Callback<OrderRes>() {
            @Override
            public void onResponse(Call<OrderRes> call, Response<OrderRes> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.ordersByUsers(response.body().getOrders());
                    }else{
                        presenter.onFailed("Couldn't get orders!");
                    }
                }else{
                    presenter.onFailed("Couldn't get orders!");
                }
            }

            @Override
            public void onFailure(Call<OrderRes> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString() );

                presenter.onFailed("Something Went Wrong!");
            }
        });

    }


    public void calcelOrders(String token, List<String> productid){
        OrderApi api = RetrofitIniti.initialize().create(OrderApi.class);
        Call<Order> responseCall = api.cancelOrders(token, productid);

        responseCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.cancelOrders(response.body().getMessage());
                    }else{
                        presenter.onFailed("Couldn't get orders!");
                    }
                }else{
                    presenter.onFailed("Couldn't get orders!");
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString() );

                presenter.onFailed("Something Went Wrong!");
            }
        });

    }
}
