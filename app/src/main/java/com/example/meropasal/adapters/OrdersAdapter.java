package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.orders.OrderRes;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.ui.order.OrdersInterface;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyHolder> {

    private Context context;
    private List<OrderRes> ordersList;
    private OrdersInterface ordersInterface;
    private List<String> productidlist = new ArrayList<>();

    public OrdersAdapter(Context context, List<OrderRes> ordersList, OrdersInterface ordersInterface) {
        this.context = context;
        this.ordersList = ordersList;
        this.ordersInterface = ordersInterface;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_orders_layout,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        OrderRes oc = ordersList.get(position);
        String imgurl = Constants.IMAGE_URL + "products/" + oc.getProductid().get_id() + "/" + oc.getProductid().getImage()[0];
        Picasso.get().load(imgurl).into(holder.productimg);

        holder.productname.setText(oc.getProductid().getName());
        holder.productprice.setText("Rs " +  Utility.getFormatedNumber(oc.getProductid().getPrice() + ""));
        holder.quantity.setText(oc.getQuantity() + "");
        holder.subtotal.setText("Rs " +  Utility.getFormatedNumber(oc.getTotal() + ""));

        holder.orderslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(holder.orderscheck.isChecked()){
                   holder.orderscheck.setChecked(false);

               }else{
                   

                   holder.orderscheck.setChecked(true);

               }
            }
        });

        holder.orderscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    productidlist.add(oc.getProductid().get_id());


                    ordersInterface.addProductIdToList(productidlist);
                }else{
                    productidlist.remove(oc.getProductid().get_id());

                    ordersInterface.addProductIdToList(productidlist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView productimg;
        TextView productname, productprice, quantity, total, subtotal, shipcharge;
        RelativeLayout orderslay;
        CheckBox orderscheck;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimg = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            quantity = itemView.findViewById(R.id.quantityview);
            total = itemView.findViewById(R.id.total);
            subtotal = itemView.findViewById(R.id.subtotal);
            shipcharge = itemView.findViewById(R.id.shipcharge);
            orderslay = itemView.findViewById(R.id.orderlay);
            orderscheck = itemView.findViewById(R.id.ordercheck);
        }
    }
}
