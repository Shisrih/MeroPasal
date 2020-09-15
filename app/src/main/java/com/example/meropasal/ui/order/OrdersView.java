package com.example.meropasal.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.adapters.OrdersAdapter;
import com.example.meropasal.models.orders.OrderRes;
import com.example.meropasal.presenters.order.OrdersPresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.views.OrdersContract;

import java.util.ArrayList;
import java.util.List;

public class OrdersView extends AppCompatActivity implements OrdersContract.View, OrdersInterface {

    private OrdersPresenter presenter;
    private RecyclerView ordersView;
    private SharedPreferences sharedPreferences;
    private String token;
    private List<String> productidList = new ArrayList<>();
    private Button cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_view);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        token = sharedPreferences.getString(Constants.TOKEN, null);
        if(token != null){
            viewInit();
        }else{
          redirect();
        }

    }

    private void redirect(){
        startActivity(new Intent(OrdersView.this, Logindashboard.class));
        finish();
    }

    private void viewInit(){
        ordersView = findViewById(R.id.orderlist);
        presenter = new OrdersPresenter(this);
        cancelbtn = findViewById(R.id.cancelorder);


        presenter.getOrdersByUser(token);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productidList.size() != 0){
                    presenter.cancelOrders(token, productidList);
                }
            }
        });
    }

    @Override
    public void ordersByUsers(List<OrderRes> orders) {
        OrdersAdapter adapter = new OrdersAdapter(this, orders, this);
        ordersView.setLayoutManager(new LinearLayoutManager(this));
        ordersView.setAdapter(adapter);
    }

    @Override
    public void cancelOrders(String message) {
        presenter.getOrdersByUser(token);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addProductIdToList(List<String> productid) {
        productidList = productid;

    }
}