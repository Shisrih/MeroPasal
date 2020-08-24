package com.example.meropasal.ui.shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.presenters.user.ShippingAddressPresenter;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Validator;
import com.example.meropasal.views.ShippingAddressContract;

import java.util.List;

public class ShippingAddressForm extends AppCompatActivity implements ShippingAddressContract.View {

    private EditText fullnametxt, phonetxt, addresstxt, citytxt, zonetxt;
    private String fullname, address, city, zone;
    private long phone;
    private Button addbutton;
    private static final String TAG = "ShippingAddressForm";
    private ShippingAddressPresenter presenter;
    private String token;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_form);

        viewInit();
    }

    private void viewInit(){
        fullnametxt = findViewById(R.id.fullname);
        phonetxt = findViewById(R.id.phonenumber);
        addresstxt  = findViewById(R.id.address);
        citytxt = findViewById(R.id.city);
        zonetxt = findViewById(R.id.zone);
        addbutton = findViewById(R.id.addbtn);

        presenter = new ShippingAddressPresenter(this);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        token = sharedPreferences.getString(Constants.TOKEN, null);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs();
            }
        });

    }

    private void validateInputs(){
        fullname = fullnametxt.getText().toString();
        phone = Long.parseLong(phonetxt.getText().toString());
        address = addresstxt.getText().toString();
        city = citytxt.getText().toString();
        zone = zonetxt.getText().toString();

        int err = 0;

        if(!Validator.validateFields(phone + "")){
            phonetxt.setError("Enter Phone Number");
            phonetxt.requestFocus();
            err++;
        }
        if(!Validator.validateFields(fullname)){
            fullnametxt.setError("Enter FullName");
            fullnametxt.requestFocus();
            err++;
        }
        if(!Validator.validateFields(address)){
            addresstxt.setError("Enter Address");
            addresstxt.requestFocus();
            err++;
        }
        if(!Validator.validateFields(city)){
            citytxt.setError("Enter City");
            citytxt.requestFocus();
            err++;
        }
        if(!Validator.validateFields(zone)){
            zonetxt.setError("Enter Zone");
            zonetxt.requestFocus();
            err++;
        }

        if(err == 0){
            addShippingAddress(fullname, phone, address, city, zone);
        }
    }

    private void addShippingAddress(String fullname, long phone, String address, String city, String zone){
        presenter.addShippingAddress(new ShippingAddress(fullname, phone, address, city, zone), token);
    }


    @Override
    public void onAddShippingAddress() {
                finish();
    }

    @Override
    public void getShippingAddress(List<ShippingAddress> shippingAddress) {

    }

    @Override
    public void onFailed(String message) {
        Log.d(TAG, "onFailed: " + message);
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}