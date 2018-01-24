package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abed.hexagonrecyclerview.view.HexagonImageView;
import com.example.musta.simplyshare.feature.R;
import com.squareup.picasso.Picasso;

/**
 * Created by musta on 24-Jan-18.
 */

public class RadarDeviceViewholder extends RecyclerView.ViewHolder {

    HexagonImageView imageView;
    String image_url;

    public RadarDeviceViewholder(View itemView) {
        super(itemView);
        imageView = (HexagonImageView) itemView.findViewById(R.id.img_view);
    }

    public void setImageUrl(String imageUrl){
        this.image_url = imageUrl;
        Picasso.with(imageView.getContext()).load(image_url).into(imageView);
        Picasso.with(imageView.getContext()).load(image_url).into(imageView);
    }
}
