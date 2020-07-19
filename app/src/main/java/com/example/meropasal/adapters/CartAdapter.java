package com.example.meropasal.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.ui.home.Cart;
import com.example.meropasal.utiils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder>  {
    private Context context;
    private List<CartModel> cartModelList;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_view,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final CartModel cartModel = cartModelList.get(position);

        holder.productname.setText(cartModel.getName());

        Picasso.get().load(cartModel.getSingleImg()).into(holder.productimage);

        final DbHelper helper = new DbHelper(context);

        holder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Remove From Cart")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        helper.deleteFromCart(cartModel.getId());
                        cartModelList.remove(cartModel);
                        notifyDataSetChanged();

                        if(cartModelList.isEmpty()){
                            Cart.setEmptycart();
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

        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return this.cartModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView productimage;
        public TextView productname;
        public LinearLayout productLayout;
        public Button removebtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productimage = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            removebtn = itemView.findViewById(R.id.removebtn);
            productLayout = itemView.findViewById(R.id.productlayout);
        }
    }
}
