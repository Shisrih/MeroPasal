package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.utiils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeProductsAdapter extends RecyclerView.Adapter<HomeProductsAdapter.MyHolder> {

    private Context context;
    private List<Product> productList;


    public HomeProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HomeProductsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.homeproducts_view,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductsAdapter.MyHolder holder, int position) {
        Product product = productList.get(position);

        String product_img = product.getImage()[0];
        String imgurl = Constants.IMAGE_URL + "products/" + product.get_id() + "/" + product_img;

//        Log.d("TAG", "onBindViewHolder: " + imgurl);

        Picasso.get().load(imgurl).into(holder.productimage);

        holder.productname.setText(product.getName());
        holder.productprice.setText("Rs " + product.getPrice());


        holder.productlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView productimage;
        public TextView productname, productprice;
        public LinearLayout productlayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            productlayout = itemView.findViewById(R.id.productlayout);

        }
    }
}
