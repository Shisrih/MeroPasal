package com.example.meropasal.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.meropasal.R;
import com.example.meropasal.adapters.ExclusiveProductAdapter;
import com.example.meropasal.adapters.ImageSliderAdapter;
import com.example.meropasal.models.ExclusiveProductScrollModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class Homescreen extends Fragment {
    private SliderView sliderview;
    private List<ExclusiveProductScrollModel> exclusiveProductScrollModelList = new ArrayList<>();
    private RecyclerView exclusiveproductsrecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_homescreen,container,false);
        sliderview = root.findViewById(R.id.imageSlider);
        exclusiveproductsrecycler = root.findViewById(R.id.exclusiveitems_stack);



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

        //Exclusive products horizontal scroll view (Instanciating Adapter)//
        ExclusiveProductAdapter exadpter = new ExclusiveProductAdapter(root.getContext(), exclusiveProductScrollModelList);
        exclusiveProductScrollModelList.add(new ExclusiveProductScrollModel(R.drawable.mobilephones,"Iphone 8 Plus(8GB RAM 256GB )","Rs.2000","Rs.3000"));
        exclusiveProductScrollModelList.add(new ExclusiveProductScrollModel(R.drawable.mobilephones,"Iphone 8 Plus(8GB RAM 256GB )","Rs.2000","Rs.3000"));
        exclusiveProductScrollModelList.add(new ExclusiveProductScrollModel(R.drawable.mobilephones,"Iphone 8 Plus(8GB RAM 256GB )","Rs.2000","Rs.3000"));
        exclusiveProductScrollModelList.add(new ExclusiveProductScrollModel(R.drawable.mobilephones,"Iphone 8 Plus(8GB RAM 256GB )","Rs.2000","Rs.3000"));

        exclusiveproductsrecycler.setLayoutManager(new LinearLayoutManager( getContext(), LinearLayoutManager.HORIZONTAL, false));
        exclusiveproductsrecycler.setAdapter(exadpter);








        return root;
    }
}