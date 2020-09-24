package com.example.meropasal.ui.acount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.meropasal.R;
import com.example.meropasal.adapters.ManageShippingAddressAdapter;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.presenters.user.ShippingAddressPresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.ui.shipping.ShippingAddressForm;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.views.ShippingAddressContract;

import java.util.List;

public class ManageShippingAddress extends AppCompatActivity implements ShippingAddressContract.View {

    private RecyclerView addresslist;
    private ShippingAddressPresenter presenter;
    private String token;
    private SharedPreferences sharedPreferences;
    private Button addaddressbtn, deladdressbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shipping_address);


        viewInit();
    }


    private void viewInit(){
        addresslist = findViewById(R.id.addresslist);

        addaddressbtn = findViewById(R.id.addaddress);
        deladdressbtn = findViewById(R.id.deladdress);


        presenter = new ShippingAddressPresenter(this);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        token = sharedPreferences.getString(Constants.TOKEN, null);

        if(token != null){
            presenter.getShippingAddress(token);
        }else{
            redirect();
        }

        addaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ManageShippingAddress.this, ShippingAddressForm.class));
            }
        });


    }

    private void redirect(){
        finish();
        startActivity(new Intent(this, Logindashboard.class));
    }



    @Override
    public void onAddShippingAddress() {

    }

    @Override
    public void getShippingAddress(List<ShippingAddress> shippingAddress) {
        ManageShippingAddressAdapter addressAdapter = new ManageShippingAddressAdapter(this, shippingAddress);

        addresslist.setLayoutManager(new LinearLayoutManager(this));
        addresslist.setAdapter(addressAdapter);
    }

    @Override
    public void onFailed(String message) {

    }
}