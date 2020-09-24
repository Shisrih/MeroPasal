package com.example.meropasal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.ui.shipping.ShippingAddressForm;

import java.util.List;

public class ManageShippingAddressAdapter extends RecyclerView.Adapter<ManageShippingAddressAdapter.MyHolder> {


    private Context context;
    private List<ShippingAddress> shippingAddressList;

    public ManageShippingAddressAdapter(Context context, List<ShippingAddress> shippingAddressList) {
        this.context = context;
        this.shippingAddressList = shippingAddressList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shipping_address_lay,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ShippingAddress sh = shippingAddressList.get(position);

        holder.fullname.setText(sh.getFullname());
        holder.phonenumber.setText(sh.getPhonenumber() + "");
        holder.address.setText(sh.getAddress());
        holder.city.setText(sh.getCity());
        holder.zone.setText(sh.getZone());


    }

    @Override
    public int getItemCount() {
        return shippingAddressList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView fullname, address, city, zone, phonenumber;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.fullname);
            address = itemView.findViewById(R.id.addresstxt);
            city = itemView.findViewById(R.id.citytxt);
            zone = itemView.findViewById(R.id.zonetxt);
            phonenumber = itemView.findViewById(R.id.phone);


        }
    }
}
