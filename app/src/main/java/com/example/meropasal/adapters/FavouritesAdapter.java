package com.example.meropasal.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.meropasal.models.products.FavModel;
import com.example.meropasal.ui.home.Cart;
import com.example.meropasal.ui.home.Favourites;
import com.example.meropasal.utiils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MyHolder> {

    private Context context;
    private List<FavModel> favModelList;


    public FavouritesAdapter(Context context, List<FavModel> favModelList) {
        this.context = context;
        this.favModelList = favModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favourites_view,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FavModel fav = favModelList.get(position);
        holder.productname.setText(fav.getName());
        holder.productprice.setText("Rs " +  Utility.getFormatedNumber(fav.getPrice() + ""));
        Picasso.get().load(fav.getSingleImg()).into(holder.productimage);


        final DbHelper helper = new DbHelper(context);

        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Remove From Cart")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                helper.deleteFromCart(fav.getId());
                                favModelList.remove(fav);
                                helper.deleteFromFav(fav.getId());



                                notifyDataSetChanged();

                                if(favModelList.isEmpty()){
                                    Favourites.setEmptyfav();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return favModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView productimage;
        public TextView productname, productprice;
        public LinearLayout productLayout;
        public TextView removebtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            removebtn = itemView.findViewById(R.id.removebtn);
            productLayout = itemView.findViewById(R.id.productlayout);
            productprice = itemView.findViewById(R.id.productprice);
        }
    }
}
