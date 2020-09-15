package com.example.meropasal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.products.Discount;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.ui.auth.MainLogin;
import com.example.meropasal.ui.product.ProductView;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ExclusiveProductAdapter extends RecyclerView.Adapter<ExclusiveProductAdapter.MyHolder> {
   private Context context;
    private List<Product> exclusiveProductScrollModelList;

    public ExclusiveProductAdapter(Context context, List<Product> exclusiveProductScrollModelList) {
        this.context = context;
        this.exclusiveProductScrollModelList = exclusiveProductScrollModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exclusive_products_recycler_layout,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Product expsm = exclusiveProductScrollModelList.get(position);

        if(expsm.getDiscount().size() != 0) {
            final Discount discount = expsm.getDiscount().get(0);

            final String product_img = expsm.getImage()[0];
            String imgurl = Constants.IMAGE_URL + "products/" + expsm.get_id() + "/" + product_img;



            Picasso.get().load(imgurl).into(holder.productimg);

            holder.productname.setText(expsm.getName());
            holder.discountvalue.setText("-" + discount.getDiscountValue() + "%");
            holder.oldprice.setText("Rs " + Utility.getFormatedNumber(expsm.getPrice()));

            float price = Float.parseFloat(expsm.getPrice());
            float discountVAl = Float.parseFloat(discount.getDiscountValue());

            float newprice = Math.round(price - (price * (discountVAl / 100)));
            DecimalFormat df = new DecimalFormat("0.##");

            holder.newprice.setText("Rs " +  Utility.getFormatedNumber(newprice + ""));


            final DbHelper helper = new DbHelper(context);

             SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);


             String userid  = sharedPreferences.getString(Constants.USER_ID, null);

            holder.cartbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Authenticator.checkLoginStatus(sharedPreferences)){


                        if(  helper.addToCart(new CartModel(0, userid, expsm.get_id(), expsm.getName(), product_img , newprice, 1, newprice))){
                            Snackbar.make(view, "Product Added To Cart", Snackbar.LENGTH_LONG).show();

                        }else{
                            Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
                        }

                    }else{
                        context.startActivity(new Intent(context.getApplicationContext(), Logindashboard.class));
                    }
                }
            });

            holder.productlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ProductView.class);

                    intent.putExtra("brand", expsm.getBrand());

                    intent.putExtra("id", expsm.get_id());

                    context.startActivity(intent);
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return exclusiveProductScrollModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

       private ImageView productimg;
       private TextView productname,newprice,oldprice, discountvalue;
       private Button cartbtn;
        private LinearLayout productlayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.exclusiveproductimg);
            productname =itemView.findViewById(R.id.exclusiveproductname);
            newprice= itemView.findViewById(R.id.latestprice);
            oldprice =itemView.findViewById(R.id.originalprice);
            cartbtn = itemView.findViewById(R.id.addcartbtn);
            discountvalue = itemView.findViewById(R.id.discountvalue);
            productlayout = itemView.findViewById(R.id.productlayout);
        }
    }
}
