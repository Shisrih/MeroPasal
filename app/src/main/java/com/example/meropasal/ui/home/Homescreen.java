package com.example.meropasal.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.meropasal.R;
import com.example.meropasal.adapters.CategoriesAdapter;
import com.example.meropasal.adapters.ExclusiveProductAdapter;
import com.example.meropasal.adapters.ImageSliderAdapter;
import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.presenters.home.HomePresenter;
import com.example.meropasal.views.HomeContract;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


public class Homescreen extends Fragment implements HomeContract.View {
    private SliderView sliderview;

    private RecyclerView exclusiveproductsrecycler, categoriesview;

    private HomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_homescreen,container,false);
        sliderview = root.findViewById(R.id.imageSlider);
        exclusiveproductsrecycler = root.findViewById(R.id.exclusiveitems_stack);
        categoriesview = root.findViewById(R.id.categoryrow);


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

        homePresenter = new HomePresenter(this);

        homePresenter.getCategories("6");
        homePresenter.getExclusiveProducts();








        return root;
    }

    @Override
    public void getCategories(List<Category> categories) {
        CategoriesAdapter adapter = new CategoriesAdapter(getContext(), categories);
        categoriesview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        categoriesview.setAdapter(adapter);
    }

    @Override
    public void getExclusiveProducts(List<Product> products) {
        //Exclusive products horizontal scroll view (Instanciating Adapter)//
        ExclusiveProductAdapter exadpter = new ExclusiveProductAdapter(getContext(),products);

        exclusiveproductsrecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        exclusiveproductsrecycler.setAdapter(exadpter);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(getContext(),   message, Toast.LENGTH_SHORT).show();
    }
}