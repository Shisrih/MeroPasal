package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.orders.OrderConfirmation;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.utiils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyHolder> {

    private Context context;
    private List<CartModel> orderList;


    public OrderListAdapter(Context context, List<CartModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_list_layout,parent,false);
       MyHolder holder = new  MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CartModel oc = orderList.get(position);
            Picasso.get().load(oc.getSingleImg()).into(holder.productimg);

            holder.productname.setText(oc.getName());
            holder.productprice.setText("Rs " +  Utility.getFormatedNumber(oc.getPrice() + ""));
            holder.quantity.setText(oc.getQuantity() + "");
            holder.subtotal.setText("Rs " +  Utility.getFormatedNumber(oc.getTotalPrice() + ""));

//            float total = oc.getTotalPrice() + oc.getShippingCharge();
//            holder.total.setText("Rs " +  Utility.getFormatedNumber(total + ""));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView productimg;
        TextView productname, productprice, quantity, total, subtotal, shipcharge;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            quantity = itemView.findViewById(R.id.quantityview);
            total = itemView.findViewById(R.id.total);
            subtotal = itemView.findViewById(R.id.subtotal);
            shipcharge = itemView.findViewById(R.id.shipcharge);
        }
    }
}
