package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.ui.product.SelectShippingAddress;

import java.util.List;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.MyHolder> {

    private Context context;
    private List<ShippingAddress> shippingAddressList;
    private int selectedPosition = -1;
    SelectShippingAddress selectShippingAddress;

    public ShippingAddressAdapter(Context context, SelectShippingAddress selectShippingAddress, List<ShippingAddress> shippingAddressList) {
        this.context = context;
        this.selectShippingAddress = selectShippingAddress;
        this.shippingAddressList = shippingAddressList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shipping_address_list_layout,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            ShippingAddress ship = shippingAddressList.get(position);

            holder.addresstxt.setText(ship.getAddress());
            holder.citytxt.setText(ship.getCity());


            if(ship.isSelected()){
                holder.checkbox.setChecked(true);
                selectedPosition = position;
            }else{
                holder.checkbox.setChecked(false);
            }



        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(shippingAddressList.size() == 1){
                    if(b){
                        selectShippingAddress.selectedShippingAddress(shippingAddressList.get(position));
                        selectShippingAddress.isShippingAddressSelected(true);
                    }else{
                        selectShippingAddress.isShippingAddressSelected(false);
                    }
                }else{
                    if(b){
                        selectShippingAddress.selectedShippingAddress(shippingAddressList.get(position));
                        selectShippingAddress.isShippingAddressSelected(true);
                        ship.setSelected(true);
                        if(selectedPosition >= 0){
                            shippingAddressList.get(selectedPosition).setSelected(false);
                            notifyDataSetChanged();
                        }
                        selectedPosition = position;
                    }
                    else{
                        ship.setSelected(false);
                        selectShippingAddress.isShippingAddressSelected(false);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return shippingAddressList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView addresstxt, citytxt;
        public CheckBox checkbox;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            addresstxt  = itemView.findViewById(R.id.addresstxt);
            citytxt = itemView.findViewById(R.id.citytxt);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
