package com.example.meropasal.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meropasal.R;
import com.example.meropasal.ui.auth.Logindashboard;


public class Account extends Fragment {
Button Loginsignupbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_account, container, false);

         Loginsignupbtn = root.findViewById(R.id.Loginbtn);

         Loginsignupbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getContext(), Logindashboard.class);
                 startActivity(intent);
             }
         });


        return root;
    }
}