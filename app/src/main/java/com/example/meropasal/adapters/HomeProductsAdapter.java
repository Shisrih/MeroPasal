package com.example.meropasal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;

import com.example.meropasal.ui.product.ProductView;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeProductsAdapter extends RecyclerView.Adapter<HomeProductsAdapter.MyHolder> {

    private Context context;
    private List<ProductRes> productList;


    public HomeProductsAdapter(Context context, List<ProductRes> productList) {
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
      final ProductRes productRes = productList.get(position);

        String product_img = productRes.getProduct().getImage()[0];
        String imgurl = Constants.IMAGE_URL + "products/" + productRes.getProduct().get_id() + "/" + product_img;

//        Log.d("TAG", "onBindViewHolder: " + imgurl);

        Picasso.get().load(imgurl).into(holder.productimage);

        holder.productname.setText(productRes.getProduct().getName());
        holder.productprice.setText("Rs " + Utility.getFormatedNumber(productRes.getProduct().getPrice()) );
        holder.ratings.setRating(productRes.getAvgRatings());

        holder.productlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductView.class);
                intent.putExtra("name", productRes.getProduct().getName());
                intent.putExtra("price", productRes.getProduct().getPrice());
                intent.putExtra("ratings", productRes.getAvgRatings());
                intent.putExtra("images", productRes.getProduct().getImage());
                intent.putExtra("brand", productRes.getProduct().getBrand());
                intent.putExtra("id", productRes.getProduct().get_id());
                intent.putExtra("details", productRes.getProduct().getDetail());


                        context.startActivity(intent);
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
        public RatingBar ratings;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            productlayout = itemView.findViewById(R.id.productlayout);
            ratings = itemView.findViewById(R.id.ratings);
        }
    }
}
