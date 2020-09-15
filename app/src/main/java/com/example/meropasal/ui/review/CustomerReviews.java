package com.example.meropasal.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meropasal.R;
import com.example.meropasal.adapters.ReviewsAdapter;
import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.review.RatingsBreakdown;
import com.example.meropasal.presenters.product.ProductReviewsPresenter;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.example.meropasal.utiils.Validator;
import com.example.meropasal.views.reviews.ProductReviewsContract;
import com.facebook.share.Share;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomerReviews extends AppCompatActivity implements ProductReviewsContract.View {

    private float avgRating;
    private String productid;
    private RatingBar ratingBar, dialogRatingBar;
    private TextView ratingtxt;
    private RecyclerView prodreviewview;
    private FloatingActionButton reviewdialogbtn;
    private Dialog dialog;
    private EditText reviewtxt;
    private String token;
    private ProductReviewsPresenter productReviewsPresenter;
    private SharedPreferences sharedPreferences;
    private float currentRating = 0;
    private String review = "";
    private TextView ratingcount5, ratingcount4, ratingcount3, ratingcount2, ratingcount1;
    private ProgressBar progress5, progress4, progress3, progress2, progress1;
    private Button submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        Intent intent = getIntent();
        avgRating = intent.getFloatExtra("avgrating", 0);
        productid = intent.getStringExtra("productid");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);

        viewInit();
        if(token != null) {
            dialogInit();
        }
        reviewdialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(token != null){
                    dialog.show();
                }
            }
        });
    }

    private void dialogInit(){
        dialog = new Dialog(this,R.style.AppTheme_ModalTheme);
        dialog.setContentView(R.layout.add_review_dialog);

        dialogRatingBar = dialog.findViewById(R.id.ratingbar);
        reviewtxt = dialog.findViewById(R.id.reviewtxt);
        submitbtn = dialog.findViewById(R.id.submitbtn);

            productReviewsPresenter.getRatingByUser(token, productid);


        dialogRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                currentRating = (int) v;

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateReview();
            }
        });

    }

    private void validateReview(){

        review = reviewtxt.getText().toString();
        int err = 0;

        if(currentRating == 0){
            err++;
        }
        if(!Validator.validateFields(review)){
            err++;
            reviewtxt.setError("Review Cannot Be Empty");
        }

        if(err == 0){
            productReviewsPresenter.addReview(token, new Rating(productid, currentRating, review));
        }
    }


    private void viewInit(){
        ratingBar = findViewById(R.id.ratings);
        ratingtxt = findViewById(R.id.ratingstxt);
        prodreviewview = findViewById(R.id.prodreviewview);
        reviewdialogbtn = findViewById(R.id.reviewdialogbtn);
        ratingcount5 = findViewById(R.id.ratingcount5);
        ratingcount4 = findViewById(R.id.ratingcount4);
        ratingcount3 = findViewById(R.id.ratingcount3);
        ratingcount2 = findViewById(R.id.ratingcount2);
        ratingcount1 = findViewById(R.id.ratingcount1);

        progress5 = findViewById(R.id.progress5);
        progress4 = findViewById(R.id.progress4);
        progress3 = findViewById(R.id.progress3);
        progress2 = findViewById(R.id.progress2);
        progress1 = findViewById(R.id.progress1);
        ratingBar.setRating(avgRating);



        String text1 = avgRating + "";
        String text2 = "/5";

        SpannableString span1 = new SpannableString(text1);
        span1.setSpan(new AbsoluteSizeSpan(70), 0, text1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString span2 = new SpannableString(text2);
        span2.setSpan(new AbsoluteSizeSpan(50), 0, text2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        CharSequence finalText = TextUtils.concat(span1, " ", span2);

        ratingtxt.setText(finalText);

        productReviewsPresenter = new ProductReviewsPresenter(this);

        productReviewsPresenter.getReviews(productid);

        productReviewsPresenter.getRatingsCount(productid);

    }

    @Override
    public void getProductReviews(List<Rating> ratingList) {
        ReviewsAdapter adapter = new ReviewsAdapter(this, ratingList);
        prodreviewview.setLayoutManager(new LinearLayoutManager(this));
        prodreviewview.setAdapter(adapter);
    }

    @Override
    public void getReviewByUser(Rating rating) {
        if(rating != null){
            currentRating = rating.getRatings();

            dialogRatingBar.setRating(rating.getRatings());

            review = rating.getReview();
            reviewtxt.setText(rating.getReview());
        }else{

        }
    }

    @Override
    public void getRatingsCount(List<RatingsBreakdown> ratingsBreakdownList) {

        for (RatingsBreakdown ratingsbreak:
             ratingsBreakdownList) {

            switch (ratingsbreak.getRating()){
                case 5:

                    setProgressBar(ratingcount5, progress5, ratingsbreak.getCount());
                    break;

                case 4:

                    setProgressBar(ratingcount4, progress4, ratingsbreak.getCount());
                    break;

                case 3:

                    setProgressBar(ratingcount3, progress3, ratingsbreak.getCount());
                    break;

                case 2:

                    setProgressBar(ratingcount2, progress2, ratingsbreak.getCount());
                    break;

                case 1:

                    setProgressBar(ratingcount1, progress1,ratingsbreak.getCount());
                    break;
            }



        }
    }

    private void setProgressBar(TextView ratingtxt, ProgressBar bar, int count){
        ratingtxt.setText(count+ "");

        if(count > 100){
            bar.setProgress(90);
        }else if(count >= 50){
            bar.setProgress(60);
        }else if(count > 20){
            bar.setProgress(30);
        }else if(count <= 10){
            bar.setProgress(10);
        }else if(count == 0){
            bar.setProgress(0);
        }
    }

    @Override
    public void onAddReview() {
        dialog.dismiss();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}