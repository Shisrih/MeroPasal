package com.example.meropasal.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.example.meropasal.R;
import com.example.meropasal.adapters.ImageSliderAdapter;
import com.example.meropasal.adapters.ProductSliderAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity {

    private static final String CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_TEST;
    private SliderView sliderview;
    private Toolbar pdttoolbar;
    private CollapsingToolbarLayout ct;
    private List<String> imgList = new ArrayList<>();
    private RatingBar prodratings;
    private  TextView slidercount, prodname, prodbrand, prodprice, proddetail, oldprice;
    private String name,brand, price, detail, id;
    private FloatingActionButton buybtn, esewabtn, cartbtn;
    private String originalprice = null;
    private int ratings;
    private ImageView backbtn;

    public static final int REQUEST_CODE_PAYMENT = 1;

    private ESewaConfiguration eSewaConfiguration;

    private static final String MERCHANT_ID = "JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R";
    private static final String MERCHANT_SECRET_KEY = "BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        sliderview = findViewById(R.id.imageSlider);
        ct = findViewById(R.id.toolbarlayout);
        pdttoolbar = findViewById(R.id.pdttoolbar);
        slidercount = findViewById(R.id.slidercount);
        prodname = findViewById(R.id.prodname);
        prodbrand = findViewById(R.id.prodbrand);
        prodratings = findViewById(R.id.prodratings);
        prodprice = findViewById(R.id.prodprice);
        proddetail = findViewById(R.id.proddetail);
        oldprice = findViewById(R.id.oldprice);
        backbtn = findViewById(R.id.backbtn);
        esewabtn = findViewById(R.id.esewabtn);
        cartbtn = findViewById(R.id.cartbtn);


         eSewaConfiguration = new ESewaConfiguration()
                .clientId(MERCHANT_ID)
                .secretKey(MERCHANT_SECRET_KEY)
                .environment(CONFIG_ENVIRONMENT);



        //Instanciating the image-slider adapter in the buymeds fragment//

        Intent intent = getIntent();
        final String images[] = intent.getStringArrayExtra("images");


        id = intent.getStringExtra("id");

        name = intent.getStringExtra("name");
        prodname.setText(name);

        brand = intent.getStringExtra("brand");
        prodbrand.setText(brand);


        price = intent.getStringExtra("price");
        prodprice.setText("Rs " + Utility.getFormatedNumber(price));


        detail = intent.getStringExtra("details");
        proddetail.setText(detail);


        originalprice = intent.getStringExtra("originalprice");


        ratings = intent.getIntExtra("ratings", 0);


        prodratings.setRating(ratings);

        for (String image:
             images) {
            imgList.add(image);
        }


        String product_id = intent.getStringExtra("id");

        ProductSliderAdapter adapter = new ProductSliderAdapter(this, imgList,product_id , slidercount);
        sliderview.setSliderAdapter(adapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderview.setIndicatorSelectedColor(Color.WHITE);
        sliderview.setIndicatorUnselectedColor(Color.WHITE);
        sliderview.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderview.startAutoCycle();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductView.super.onBackPressed();
            }
        });

        esewabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ESewaPayment eSewaPayment = new ESewaPayment(price,
        name, id,"<call_back_url>");

                Intent intent = new Intent(ProductView.this, ESewaPaymentActivity.class);
                intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration);

                intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }
        });

        final DbHelper helper = new DbHelper(this);

        final SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);


        final String userid  = sharedPreferences.getString(Constants.USER_ID, null);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Authenticator.checkLoginStatus(sharedPreferences)){
                    helper.addToCart(new CartModel(0, userid, id, name, images[0] ));
                }else{
                    startActivity(new Intent(getApplicationContext(), Logindashboard.class));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof of Payment", s);
                Toast.makeText(this, "SUCCESSFUL PAYMENT", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled By User", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof of Payment", s);
            }
        }
    }

}