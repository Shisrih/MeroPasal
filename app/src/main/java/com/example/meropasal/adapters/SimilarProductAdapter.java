package com.example.meropasal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.ui.product.ProductView;
import com.example.meropasal.utiils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.MyHolder> {

    private Context context;
    private List<Product> productList;

    public SimilarProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.similar_product_view,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Product product = productList.get(position);
        String product_img = product.getImage()[0];
        String imgurl = Constants.IMAGE_URL + "products/" + product.get_id() + "/" + product_img;

 


        Picasso.get().load(imgurl).into(holder.productimage);
        holder.productname.setText(product.getName());

        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
                Intent intent = new Intent(context, ProductView.class);

                intent.putExtra("brand", product.getBrand());

                intent.putExtra("id", product.get_id());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView productimage;
        TextView productname;
        LinearLayout productLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            productLayout = itemView.findViewById(R.id.productlayout);
        }
    }
}
