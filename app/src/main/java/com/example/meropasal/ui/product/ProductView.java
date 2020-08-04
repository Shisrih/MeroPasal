package com.example.meropasal.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.meropasal.adapters.SimilarProductAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.products.Discount;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.presenters.product.ProductPresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.example.meropasal.views.ProductContract;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity implements ProductContract.View {

    private static final String CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_TEST;
    private SliderView sliderview;
    private Toolbar pdttoolbar;
    private CollapsingToolbarLayout ct;
    private List<String> imgList = new ArrayList<>();
    private RatingBar prodratings;
    private  TextView slidercount, prodname, prodbrand, prodprice, proddetail, oldprice;
    private Button buynowbtn,cartbtn;
    private RecyclerView similarProductsView;
    private String name,brand, price, detail, id;


    //no Floating Action Button needed in Product view, instead a separate activity for payment option is created opened through buy  now button//
    private FloatingActionButton buybtn, esewabtn;
    private String originalprice = null;
    private int ratings;
    private ImageView backbtn;
    private ProductPresenter presenter;

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
        buynowbtn =findViewById(R.id.buynowbtn);
        backbtn = findViewById(R.id.backbtn);
        esewabtn = findViewById(R.id.esewabtn);
        cartbtn = findViewById(R.id.cartbtn);
        similarProductsView = findViewById(R.id.similarproductview);

         eSewaConfiguration = new ESewaConfiguration()
                .clientId(MERCHANT_ID)
                .secretKey(MERCHANT_SECRET_KEY)
                .environment(CONFIG_ENVIRONMENT);



        //Instanciating the image-slider adapter in the buymeds fragment//

        Intent intent = getIntent();
        final String images[] = intent.getStringArrayExtra("images");





        presenter = new ProductPresenter(this);

        id = intent.getStringExtra("id");
        brand = intent.getStringExtra("brand");


        presenter.getProductById(id);
        presenter.getProductsByBrand(brand, id);




        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductView.super.onBackPressed();
            }
        });

        buynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductView.this,PaymentOptions.class);
                startActivity(intent);
            }
        });


        //eSEWA Payment optio in seperate activity//
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

    @Override
    public void getSimilarProducts(List<Product> product) {
        SimilarProductAdapter adapter = new SimilarProductAdapter(this, product);
        similarProductsView.setAdapter(adapter);
        similarProductsView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void onSuccess(Product product, int rating) {
        prodname.setText(product.getName());


        prodbrand.setText(product.getBrand());

        prodratings.setRating(rating);


        if( product.getDiscount().size() != 0) {
            oldprice.setVisibility(View.VISIBLE);

            Discount discount = product.getDiscount().get(0);
            float price = Float.parseFloat(product.getPrice());
            float discountVAl = Float.parseFloat(discount.getDiscountValue());

            float newprice = Math.round(price - (price * (discountVAl / 100)));

            prodprice.setText("Rs " +  Utility.getFormatedNumber(newprice + ""));
            oldprice.setText("Rs " + Utility.getFormatedNumber(product.getPrice()));
        }else{
            prodprice.setText("Rs " + Utility.getFormatedNumber(product.getPrice()));
        }

        proddetail.setText(product.getDetail());

        for (String image:
                product.getImage()) {
            imgList.add(image);
        }

        ProductSliderAdapter adapter = new ProductSliderAdapter(this, imgList, product.get_id() , slidercount);
        sliderview.setSliderAdapter(adapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderview.setIndicatorSelectedColor(Color.WHITE);
        sliderview.setIndicatorUnselectedColor(Color.WHITE);
        sliderview.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderview.startAutoCycle();


    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}