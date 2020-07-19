package com.example.meropasal.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.adapters.CartAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.ui.auth.MainLogin;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {

    private RecyclerView cartview;
    public static LinearLayout emptycart, cartlayout, notsignin;
    private  List<CartModel> cart;
    private Button signinbtn;
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

        DbHelper helper = new DbHelper(getContext());






        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString(Constants.USER_ID, null);
        cart =  helper.getFromCart(userid);


        if(Authenticator.checkLoginStatus(sharedPreferences)){
            if(!cart.isEmpty()){
                showCart();
            }else{
                setEmptycart();
            }
        }else{
            showNotSignedIn();
        }

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), MainLogin.class));
            }
        });


    }

    private void showNotSignedIn() {
        emptycart.setVisibility(View.GONE);
        cartlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.VISIBLE);
    }


    private void showCart(){
        emptycart.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
        cartlayout.setVisibility(View.VISIBLE);
        CartAdapter adapter = new CartAdapter(getContext(), cart);


        cartview.setLayoutManager(new GridLayoutManager(getContext(), 2));

        cartview.setAdapter(adapter);

    }

    public static void setEmptycart(){
        emptycart.setVisibility(View.VISIBLE);
        cartlayout.setVisibility(View.GONE);
        notsignin.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

}
