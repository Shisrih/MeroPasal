package com.example.meropasal.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.ui.auth.MainLogin;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.squareup.picasso.Picasso;

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

       final String product_img = expsm.getImage()[0];
        String imgurl = Constants.IMAGE_URL + "products/" + expsm.get_id() + "/" + product_img;



        Picasso.get().load(imgurl).into(holder.productimg);

        holder.productname.setText(expsm.getName());
        holder.oldprice.setText("Rs " + expsm.getPrice());
        holder.newprice.setText("Rs " + expsm.getPrice());
        final DbHelper helper = new DbHelper(context);

        final SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);

        final String userid  = sharedPreferences.getString(Constants.USER_ID, null);

        holder.cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(Authenticator.checkLoginStatus(sharedPreferences)){
                  helper.addToCart(new CartModel(0, userid, expsm.get_id(), expsm.getName(), product_img ));
              }else{
                  context.startActivity(new Intent(context.getApplicationContext(), MainLogin.class));
              }
            }
        });

    }

    @Override
    public int getItemCount() {
        return exclusiveProductScrollModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

       private ImageView productimg;
       private TextView productname,newprice,oldprice;
       private Button cartbtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.exclusiveproductimg);
            productname =itemView.findViewById(R.id.exclusiveproductname);
            newprice= itemView.findViewById(R.id.latestprice);
            oldprice =itemView.findViewById(R.id.originalprice);
            cartbtn = itemView.findViewById(R.id.addcartbtn);
        }
    }
}
