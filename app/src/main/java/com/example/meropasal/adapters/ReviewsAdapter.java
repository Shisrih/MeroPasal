package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meropasal.R;
import com.example.meropasal.models.review.Rating;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyHolder> {

    private Context context;
    private List<Rating> ratingsList;

    public ReviewsAdapter(Context context, List<Rating> ratingsList) {
        this.context = context;
        this.ratingsList = ratingsList;
    }




    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_review_layout,parent,false);
        MyHolder holder = new MyHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Rating rating = ratingsList.get(position);
        String fullname = rating.getUser().getFirstname() + " " + rating.getUser().getLastname();
        holder.username.setText(fullname);

        holder.rating.setRating(rating.getRatings());
        holder.review.setText(rating.getReview());

        String date = new SimpleDateFormat("yyyy/MM/dd").format(rating.getCreatedAt());
            holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
            return ratingsList.size();
        }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView username, date, review;
        RatingBar rating;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.reviewdate);
            rating = itemView.findViewById(R.id.rating);
            review = itemView.findViewById(R.id.comment);
        }
    }
}
