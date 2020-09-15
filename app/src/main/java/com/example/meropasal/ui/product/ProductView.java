package com.example.meropasal.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.meropasal.R;
import com.example.meropasal.adapters.ProductSliderAdapter;
import com.example.meropasal.adapters.ReviewsAdapter;
import com.example.meropasal.adapters.ShippingAddressAdapter;
import com.example.meropasal.adapters.SimilarProductAdapter;
import com.example.meropasal.data.database.DbHelper;
import com.example.meropasal.models.orders.OrderConfirmation;
import com.example.meropasal.models.products.CartModel;
import com.example.meropasal.models.products.Discount;
import com.example.meropasal.models.products.FavModel;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.presenters.product.ProductPresenter;
import com.example.meropasal.presenters.user.ShippingAddressPresenter;
import com.example.meropasal.ui.auth.Logindashboard;
import com.example.meropasal.ui.review.CustomerReviews;
import com.example.meropasal.ui.shipping.ShippingAddressForm;
import com.example.meropasal.utiils.AppBarStateChangeListener;
import com.example.meropasal.utiils.Authenticator;
import com.example.meropasal.utiils.Constants;
import com.example.meropasal.utiils.Utility;
import com.example.meropasal.views.ProductContract;
import com.example.meropasal.views.ShippingAddressContract;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity implements ProductContract.View, ShippingAddressContract.View, SelectShippingAddress{

    private SliderView sliderview;
    private Toolbar pdttoolbar;
    private CollapsingToolbarLayout ct;
    private List<String> imgList = new ArrayList<>();
    private RatingBar prodratings;
    private LoaderTextView slidercount, prodname, prodbrand, prodprice, proddetail, oldprice;
    private Button buynowbtn,cartbtn;
    private RecyclerView similarProductsView;
    private String name,brand, detail, id;
    private AppBarLayout mAppBarLayout;
    private Dialog dialog;
    //no Floating Action Button needed in Product view, instead a separate activity for payment option is created opened through buy  now button//
    private FloatingActionButton buybtn;
    private String originalprice = null;
    private int ratings;
    private ImageView backbtn;
    private ProductPresenter presenter;
    private String productid;
    private static final String TAG = "ProductView";
    private TextView ratingstxt, reviewlink;
    private RecyclerView reviewsview;
    private LottieAnimationView favbtn;


    private List<ShippingAddress> shippingAddressList = new ArrayList<>();
    private ShippingAddressPresenter shippingAddressPresenter;
    private ImageView prodimg, closedialog;
    private TextView productprice, proddiscount, quantityview;
    private Button placeorder;
    private SharedPreferences sharedPreferences;
    private String token;
    private RecyclerView shippingAddressView;
    private Button addAddressBtn, addbtn, minusbtn;
    private ShippingAddress shipAddress;
    public int quantity;
    private float total;
    private boolean isAddressSelected = false;



    private int favFlag = 0;

    private float finalPrice, price;

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
        mAppBarLayout = findViewById(R.id.appBar);
        cartbtn = findViewById(R.id.cartbtn);
        similarProductsView = findViewById(R.id.similarproductview);
        ratingstxt = findViewById(R.id.ratingstxt);
        reviewsview = findViewById(R.id.reviewsview);
        reviewlink = findViewById(R.id.reviewlink);
        favbtn = findViewById(R.id.favbtn);

        shipAddress = new ShippingAddress();







        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);


        if(token != null){
            shippingAddressPresenter = new ShippingAddressPresenter(this);

            shippingAddressPresenter.getShippingAddress(token);
        }





        //Instanciating the image-slider adapter in the buymeds fragment//
        Intent intent = getIntent();
        final String images[] = intent.getStringArrayExtra("images");



        //Change toolbar color when collapse
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                int toolbarBackground = (state == AppBarStateChangeListener.State.COLLAPSED) ? R.color.transparent : R.color.color_non_collapsed;
                pdttoolbar.setBackgroundColor(ContextCompat.getColor(ProductView.this, toolbarBackground));
            }
        });


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
             if(token == null){

             }else{
                 dialog.show();
             }
            }
        });




          DbHelper helper = new DbHelper(this);


        final String userid  = sharedPreferences.getString(Constants.USER_ID, null);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Authenticator.checkLoginStatus(sharedPreferences)){

             helper.addToCart(new CartModel(0, userid, id, name, imgList.get(0) , price, 1, price));
                       Snackbar.make(view, "Product Added To Cart", Snackbar.LENGTH_LONG).show();



                }else{
                    startActivity(new Intent(getApplicationContext(), Logindashboard.class));
                }
            }
        });


        if(helper.checkFav(new FavModel(userid, id))){
            favbtn.playAnimation();
            favFlag = 1;
        }

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favFlag == 0){
                    favbtn.playAnimation();
                    helper.addToFav(new FavModel(0, userid, id, name, imgList.get(0) , price));
                    Snackbar.make(view, "Product Added To Favourites", Snackbar.LENGTH_LONG).show();
                    favFlag = 1;
                }else{
                    favbtn.setProgress(0);
                    helper.unFavourite(new FavModel(userid, id));
                    favFlag = 0;
                }

            }
        });
    }


    private void dialogInit(boolean isDiscounted, float discountVAl){

        dialog = new Dialog(this,R.style.AppTheme_ModalTheme);
        dialog.setContentView(R.layout.purchase_info_layout);
        closedialog = dialog.findViewById(R.id.closedialog);
        placeorder = dialog.findViewById(R.id.placeorder);
        proddiscount = dialog.findViewById(R.id.discounttxt);
        shippingAddressView = dialog.findViewById(R.id.shipaddresslist);
        addAddressBtn = dialog.findViewById(R.id.addshipaddressbtn);
        addbtn  = dialog.findViewById(R.id.buttonadd);
        quantityview = dialog.findViewById(R.id.quantityview);
        minusbtn = dialog.findViewById(R.id.buttonminus);

        quantity = 1;
        total = price * quantity;


        dialog.getWindow().getAttributes().windowAnimations = R.style.MaterialDialogSheetAnimation;

        productprice = dialog.findViewById(R.id.prodprice);
        proddiscount = dialog.findViewById(R.id.discounttxt);
        prodimg = dialog.findViewById(R.id.prodimg);

        if(shippingAddressList.size() == 0){
            shippingAddressView.setVisibility(View.GONE);
            addAddressBtn.setVisibility(View.VISIBLE);
        }else{

            ShippingAddressAdapter addressAdapter = new ShippingAddressAdapter(this, this, shippingAddressList);
            shippingAddressView.setLayoutManager(new LinearLayoutManager(this));
            shippingAddressView.setAdapter(addressAdapter);
            shippingAddressView.setVisibility(View.VISIBLE);
            addAddressBtn.setVisibility(View.GONE);
        }


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductView.this, ShippingAddressForm.class));
            }
        });


        String imgurl = Constants.IMAGE_URL + "products/" + id + "/" + imgList.get(0);
        Picasso.get().load(imgurl).into(prodimg);

        String userid = sharedPreferences.getString(Constants.USER_ID, null);

        if(isDiscounted){
            proddiscount.setText("Discount of " + discountVAl + "% available");
        }else{

            proddiscount.setText("No discount available for this product");
        }
        String pricetext = "<font color=#000>/piece</font>";
        productprice.setText("Rs " +  Utility.getFormatedNumber(price + "") + " " + Html.fromHtml(pricetext));

        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isAddressSelected){

                   CartModel cartModel = new CartModel(userid, productid, name,imgurl, price, quantity, total );
                   OrderConfirmation oc = new OrderConfirmation(shipAddress, cartModel, 50, total);


                   Intent intent = new Intent(ProductView.this, OrderConfirm.class);
                   intent.putExtra("order", oc);
                  startActivity(intent);
                  dialog.dismiss();
               }else{
                   Snackbar.make(view, "Select Shipping Address", Snackbar.LENGTH_LONG).show();
               }

            }
        });


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                if(quantity > 5){
                    quantity = 1;
                }
                total = price * quantity;
                quantityview.setText(quantity + "");

            }
        });


        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity--;
                if(quantity < 1){
                    quantity = 1;
                }
                total = price * quantity;
                quantityview.setText(quantity + "");
                Toast.makeText(ProductView.this, total + "", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void getSimilarProducts(List<Product> product) {
        SimilarProductAdapter adapter = new SimilarProductAdapter(this, product);
        similarProductsView.setAdapter(adapter);
        similarProductsView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onSuccess(Product product, float rating) {
        prodname.setText(product.getName());
        name = product.getName();
        productid = product.get_id();

        presenter.getReviews(product.get_id());
        prodbrand.setText(product.getBrand());

        prodratings.setRating(rating);
        ratingstxt.setText(rating + "/5");



        for (String image:
                product.getImage()) {
            imgList.add(image);
        }

        if( product.getDiscount().size() != 0) {
            //INITIALIZING DIALOG

            oldprice.setVisibility(View.VISIBLE);

            Discount discount = product.getDiscount().get(0);
            float pricee = Float.parseFloat(product.getPrice());
            float discountVAl = Float.parseFloat(discount.getDiscountValue());

            float newprice = Math.round(pricee - (pricee * (discountVAl / 100)));
            price = newprice;
            prodprice.setText("Rs " +  Utility.getFormatedNumber(newprice + ""));
            oldprice.setText("Rs " + Utility.getFormatedNumber(product.getPrice()));

            dialogInit(true, discountVAl);
        }else{
            price = Float.parseFloat(product.getPrice());
            prodprice.setText("Rs " + Utility.getFormatedNumber(product.getPrice()));
            dialogInit(false,  0);
            oldprice.setVisibility(View.GONE);
        }

        proddetail.setText(product.getDetail());

        ProductSliderAdapter adapter = new ProductSliderAdapter(this, imgList, product.get_id() , slidercount);
        sliderview.setSliderAdapter(adapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderview.setIndicatorSelectedColor(Color.WHITE);
        sliderview.setIndicatorUnselectedColor(Color.WHITE);
        sliderview.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderview.startAutoCycle();

        reviewlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent  = new Intent(ProductView.this, CustomerReviews.class);
                intent.putExtra("avgrating", rating);
                intent.putExtra("productid", productid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAddShippingAddress() {

    }

    @Override
    public void getShippingAddress(List<ShippingAddress> shippingAddress) {
           this.shippingAddressList = shippingAddress;

    }

    @Override
    public void getProductReviews(List<Rating> ratingList) {

        ReviewsAdapter adapter = new ReviewsAdapter(this, ratingList);
        reviewsview.setLayoutManager(new LinearLayoutManager(this));
        reviewsview.setAdapter(adapter);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedShippingAddress(ShippingAddress shippingAddress) {
        this.shipAddress = shippingAddress;

    }

    @Override
    public void isShippingAddressSelected(boolean isSelected) {
            isAddressSelected = isSelected;
    }
}