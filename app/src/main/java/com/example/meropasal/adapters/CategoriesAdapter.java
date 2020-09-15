package com.example.meropasal.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.meropasal.models.products.Category;
import com.example.meropasal.ui.search.SearchActivity;
import com.example.meropasal.utiils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoriesAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_recyclerview,parent,false);
         MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Category category = categoryList.get(position);

        String url = Constants.IMAGE_URL + "categories/" + category.getImage();

        holder.name.setText(category.getCategory_name());
        Picasso.get().load(url).into(holder.image);

        holder.categorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.putExtra("search", category.get_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView name;
        public LinearLayout categorylayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            categorylayout = itemView.findViewById(R.id.categorylayout);
            image = itemView.findViewById(R.id.categoryimg);
            name = itemView.findViewById(R.id.categoryname);
        }
    }
}
