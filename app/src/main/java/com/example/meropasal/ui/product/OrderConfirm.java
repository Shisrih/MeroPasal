package com.example.meropasal.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.adapters.OrderListAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.orders.Order;
import com.example.meropasal.models.orders.OrderConfirmation;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.presenters.order.CartOrdersPresenter;
import com.example.meropasal.presenters.order.ConfirmOrderPresenter;
import com.example.meropasal.ui.home.Cart;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.example.meropasal.views.CartOrdersContract;
import com.example.meropasal.views.ConfirmOrderContract;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirm extends AppCompatActivity implements ConfirmOrderContract.View, CartOrdersContract.View {


    private RecyclerView orderListView;
    private  OrderConfirmation oc;
    private List<CartModel> orderList = new ArrayList<>();
    private TextView shipaddress, shipcharge, finalprice;
    private CheckBox agreeBox;
    private Boolean isAgree = false;
    private ConfirmOrderPresenter presenter;
    private Button confirmorder;
    private String token;
    private String userid, productid, paymentOption;
    private SharedPreferences sharedPreferences;
    private float quantity, total;
    private ShippingAddress shippingAddress;
    private Boolean isPaid, isDelivered;
    private List<Order> ordersList = new ArrayList<>();
    private CartOrdersPresenter cartOrdersPresenter;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);



        if(getIntent().getExtras() != null){
           oc = (OrderConfirmation) getIntent().getSerializableExtra("order");

           if(oc.getCartList() != null){
               orderList = oc.getCartList();
           }else{
               orderList.add(oc.getCart());
           }

        }

        viewInit();


    }

    private void viewInit(){
        shipaddress = findViewById(R.id.shipaddress);
        shipcharge = findViewById(R.id.shipcharge);
        finalprice = findViewById(R.id.finalprice);
        agreeBox = findViewById(R.id.agreeBox);
        orderListView = findViewById(R.id.orderlist);
        confirmorder = findViewById(R.id.confirmorder);

        presenter = new ConfirmOrderPresenter(this);
        cartOrdersPresenter = new CartOrdersPresenter(this);
        dbHelper = new DbHelper(this);


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);


        shippingAddress = oc.getShippingAddress();
        paymentOption = "Pay On Delivery";
        isPaid = false;
        isDelivered = false;


        if(oc.getCartList() != null){

            for (int i =0; i < oc.getCartList().size(); i++){
                CartModel cartmodel = oc.getCartList().get(i);
                ordersList.add(new Order(cartmodel.getUserid(), cartmodel.getProductId(), cartmodel.getQuantity(), cartmodel.getTotalPrice(), shippingAddress, paymentOption, isPaid, isDelivered));
            }

        }else{
            userid = oc.getCart().getUserid();
            productid = oc.getCart().getProductId();
            quantity = oc.getCart().getQuantity();
        }




        OrderListAdapter adapter = new OrderListAdapter(this, orderList);
        orderListView.setLayoutManager(new LinearLayoutManager(this));
        orderListView.setAdapter(adapter);

        shipaddress.setText(oc.getShippingAddress().getAddress());
        shipcharge.setText("Rs " +  Utility.getFormatedNumber(oc.getShippingCharge() + ""));

        total = oc.getSubTotal() + oc.getShippingCharge();

        finalprice.setText("Rs " +  Utility.getFormatedNumber(total + ""));


        agreeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isAgree = true;
                }else{
                    isAgree = false;
                }
            }
        });

        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAgree){
                    if(oc.getCartList() != null){
                        confirmOrders();
                    }else{
                        confirmOrder();
                    }


                }else{
                    agreeBox.setError("You must agree with the Terms and Conditions!");
                }
            }
        });

    }

    private void confirmOrder(){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Order?")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        presenter.addOrder(token , new Order(userid, productid, quantity, total, shippingAddress, paymentOption, isPaid, isDelivered));

                    }
                }).show();


    }
    private void confirmOrders(){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Order?")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        cartOrdersPresenter.addOrders(ordersList, token);

                    }
                }).show();


    }
    @Override
    public void onSuccess() {
        dbHelper.deleteAllCart();
        Intent intent = new Intent(OrderConfirm.this, PaymentOptions.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}