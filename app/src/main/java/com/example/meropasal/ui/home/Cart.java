package com.example.meropasal.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.adapters.CartAdapter;
import com.example.meropasal.adapters.ShippingAddressAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.orders.OrderConfirmation;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.presenters.user.ShippingAddressPresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.ui.product.OrderConfirm;
import com.example.meropasal.ui.product.ProductView;
import com.example.meropasal.ui.product.SelectShippingAddress;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.example.meropasal.views.ShippingAddressContract;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment implements ShippingAddressContract.View, SelectShippingAddress, CartUpdateInterface {

    private RecyclerView cartview;
    public static LinearLayout emptycart, notsignin;
    public static RelativeLayout cartlayout;
    private  List<CartModel> cart;
    private DbHelper helper;
    private Button signinbtn;
    private TextView carttotal;
    private float totalprice ;
    private ShippingAddressPresenter presenter;

    private Dialog dialog;
    private SharedPreferences sharedPreferences;

    private Button checkoutbtn, addAddressBtn, continuebtn;
    private RecyclerView shippingAddressView;
    private ShippingAddress shipAddress;

    private boolean isAddressSelected = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        setRetainInstance(true);
        viewInit(root);

        return root;
    }

    private void viewInit(View root) {
        cartview = root.findViewById(R.id.cartview);
        emptycart = root.findViewById(R.id.emptycart);
        cartlayout = root.findViewById(R.id.cartlayout);
        notsignin = root.findViewById(R.id.notsignin);
        signinbtn  = root.findViewById(R.id.signinbtn);
        carttotal = root.findViewById(R.id.carttotal);

        checkoutbtn = root.findViewById(R.id.cartcont);


        helper = new DbHelper(getContext());


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), Logindashboard.class));
            }
        });

        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

    }

    private void dialogInit(List<ShippingAddress> shippingAddressList){
        dialog = new Dialog(getContext(),R.style.AppTheme_ModalTheme);
        dialog.setContentView(R.layout.select_ship_address_dialog);

        shippingAddressView = dialog.findViewById(R.id.shipaddresslist);
        addAddressBtn = dialog.findViewById(R.id.addshipaddressbtn);
        continuebtn = dialog.findViewById(R.id.placeorder);

        if(shippingAddressList.size() > 0){
            ShippingAddressAdapter addressAdapter = new ShippingAddressAdapter(getContext(), this, shippingAddressList);
            shippingAddressView.setLayoutManager(new LinearLayoutManager(getContext()));
            shippingAddressView.setAdapter(addressAdapter);
            shippingAddressView.setVisibility(View.VISIBLE);
            addAddressBtn.setVisibility(View.GONE);
        }else{
            shippingAddressView.setVisibility(View.GONE);
            addAddressBtn.setVisibility(View.VISIBLE);
        }

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddressSelected){

                    OrderConfirmation oc = new OrderConfirmation(shipAddress, cart , 50, totalprice);
                    Intent intent = new Intent(getActivity(), OrderConfirm.class);
                    intent.putExtra("order", oc);
                    startActivity(intent);
                    dialog.dismiss();
                }else{
                    Snackbar.make(view, "Select Shipping Address", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    private void showNotSignedIn() {
        emptycart.setVisibility(View.GONE);
        cartlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.VISIBLE);
    }


    private void showCart(){
        totalprice = 0;
        emptycart.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
        cartlayout.setVisibility(View.VISIBLE);
        CartAdapter adapter = new CartAdapter(getContext(), cart, totalprice, carttotal, this);



        cartview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        for (CartModel cartModel:
                cart) {
            totalprice = cartModel.getTotalPrice() + totalprice;
        }

        carttotal.setText("Total : Rs " + Utility.getFormatedNumber(totalprice + "") );

        cartview.setAdapter(adapter);

    }

    public static void setEmptycart(){
        emptycart.setVisibility(View.VISIBLE);
        cartlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString(Constants.USER_ID, null);
        cart =  helper.getFromCart(userid);

         String token = sharedPreferences.getString(Constants.TOKEN, null);


        if(token != null){
            presenter = new ShippingAddressPresenter(this);
            presenter.getShippingAddress(token);
        }


        if(Authenticator.checkLoginStatus(sharedPreferences)){
            if(!cart.isEmpty()){
                showCart();
            }else{
                setEmptycart();
            }
        }else{
            showNotSignedIn();
        }
    }

    @Override
    public void onAddShippingAddress() {

    }

    @Override
    public void getShippingAddress(List<ShippingAddress> shippingAddress) {
        dialogInit(shippingAddress);

    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedShippingAddress(@Nullable ShippingAddress shippingAddress) {
        this.shipAddress = shippingAddress;
    }

    @Override
    public void isShippingAddressSelected(boolean isSelected) {
        isAddressSelected = isSelected;
    }

    @Override
    public void updateCart(List<CartModel> cartModelList) {
        cart = cartModelList;
    }
}
