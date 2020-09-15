package com.example.meropasal.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.meropasal.R;
import com.example.meropasal.adapters.CategoriesAdapter;
import com.example.meropasal.adapters.ExclusiveProductAdapter;
import com.example.meropasal.adapters.HomeProductsAdapter;
import com.example.meropasal.adapters.ImageSliderAdapter;
import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.presenters.home.HomePresenter;
import com.example.meropasal.ui.search.SearchActivity;
import com.example.meropasal.views.HomeContract;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class Homescreen extends Fragment implements HomeContract.View {
    private SliderView sliderview;
    private View root = null;
        private EditText searchbar;
    private RecyclerView exclusiveproductsrecycler, categoriesview, homeproductsview;
    private  SwipeRefreshLayout pullToRefresh;
    private HomePresenter homePresenter;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(root == null){
            root = inflater.inflate(R.layout.fragment_homescreen,container,false);
            setRetainInstance(true);
            viewInit(root);

            apiCalls();

        }
        //Instanciating the image-slider adapter in the buymeds fragment//
        ImageSliderAdapter adapter = new ImageSliderAdapter(root.getContext());
        sliderview.setSliderAdapter(adapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderview.setIndicatorSelectedColor(Color.WHITE);
        sliderview.setIndicatorUnselectedColor(Color.WHITE);
        sliderview.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderview.startAutoCycle();


        return root;
    }

    private void viewInit(View root){
        sliderview = root.findViewById(R.id.imageSlider);
        exclusiveproductsrecycler = root.findViewById(R.id.exclusiveitems_stack);
        categoriesview = root.findViewById(R.id.categoryrow);
        homeproductsview = root.findViewById(R.id.homeproductsview);


        homePresenter = new HomePresenter(this);
        searchbar = root.findViewById(R.id.searchbar);


        pullToRefresh  = root.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiCalls();
                pullToRefresh.setRefreshing(false);

            }
        });

        searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    performSearch();
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch(){
        String search = searchbar.getText().toString();
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra("search", search);
        startActivity(intent);
    }

    private void apiCalls(){
        homePresenter.getCategories("6");
        homePresenter.getExclusiveProducts();
        homePresenter.getLatestProducts();
    }

    @Override
    public void getLatestProducts(List<ProductRes> products) {
        HomeProductsAdapter adapter = new HomeProductsAdapter(getContext(), products);
        homeproductsview.setLayoutManager(new StaggeredGridLayoutManager(2  , LinearLayoutManager.VERTICAL));

        homeproductsview.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(homeproductsview, false);
    }

    @Override
    public void getCategories(List<Category> categories) {
        CategoriesAdapter adapter = new CategoriesAdapter(getContext(), categories);
        categoriesview.setLayoutManager(new GridLayoutManager(getContext(), 3));

        categoriesview.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(categoriesview, false);
    }

    @Override
    public void getExclusiveProducts(List<Product> products) {
        //Exclusive products horizontal scroll view (Instanciating Adapter)//
        ExclusiveProductAdapter exadpter = new ExclusiveProductAdapter(getContext(),products);

        exclusiveproductsrecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        exclusiveproductsrecycler.setAdapter(exadpter);
        ViewCompat.setNestedScrollingEnabled(exclusiveproductsrecycler, false);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(getContext(),   message, Toast.LENGTH_SHORT).show();
    }
}