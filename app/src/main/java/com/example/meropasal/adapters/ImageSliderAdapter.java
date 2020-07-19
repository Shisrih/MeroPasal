package com.example.meropasal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.meropasal.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {
    private Context context;

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_image_items_layout,null);
        return new SliderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ad5)
                        .fitCenter()
                        .into(viewHolder.sliderImageView);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ad2)
                        .fitCenter()
                        .into(viewHolder.sliderImageView);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ad3)
                        .fitCenter()
                        .into(viewHolder.sliderImageView);
                break;
            case 3:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ad6)

                        .into(viewHolder.sliderImageView);
                break;
            default:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ad4)
                        .fitCenter()
                        .into(viewHolder.sliderImageView);
                break;

        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    ImageView sliderImageView;
    public SliderViewHolder(View itemView) {
        super(itemView);
        sliderImageView = itemView.findViewById(R.id.imageView);
    }
}

