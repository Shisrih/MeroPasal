package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meropasal.R;
import com.example.meropasal.utiils.Constants;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductSliderAdapter extends SliderViewAdapter<ProductSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> images;
    private String productId;
    private TextView slidercount;

    public ProductSliderAdapter(Context context, List<String> images, String productId, TextView slidercount) {
        this.context = context;
        this.images = images;
        this.productId = productId;
        this.slidercount = slidercount;
    }

    @Override
    public ProductSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.product_image_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(ProductSliderAdapter.SliderAdapterVH viewHolder, int position) {
        String image = images.get(position);

        String imgurl = Constants.IMAGE_URL + "products/" + productId + "/" + image;

        int imagenum = position + 1;
        String count = imagenum + " / " + images.size();

        slidercount.setText(count);

        Picasso.get().load(imgurl).into(viewHolder.productimg);

    }

    @Override
    public int getCount() {
        return images.size();
    }


    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        public ImageView productimg;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);

        }
    }
}
