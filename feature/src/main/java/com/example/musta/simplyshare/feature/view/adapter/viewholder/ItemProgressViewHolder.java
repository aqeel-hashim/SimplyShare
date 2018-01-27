package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class ItemProgressViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar progressBar;
    private ImageView itemimage;
    private TextView name;
    private View mainView;

    public ItemProgressViewHolder(View itemView) {
        super(itemView);
        mainView = itemView;
        progressBar = itemView.findViewById(R.id.itemProgress);
        itemimage = itemView.findViewById(R.id.itemImage);
        name = itemView.findViewById(R.id.itemName);
    }

    public void bind(ItemModel model) {
        itemimage.setImageDrawable(model.getIcon());
        name.setText(model.getName());
        progressBar.setProgress(50, true);
        float deviceScreenHeight = mainView.getContext().getResources().getDisplayMetrics().heightPixels;

    }
}
