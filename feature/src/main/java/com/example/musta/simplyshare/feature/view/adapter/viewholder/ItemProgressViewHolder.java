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

    private transient ProgressBar progressBar;
    private transient ImageView itemimage;
    private transient TextView name;
    private transient TextView size;
    private transient TextView percentage;
    private transient View mainView;

    public ItemProgressViewHolder(View itemView) {
        super(itemView);
        mainView = itemView;
        progressBar = itemView.findViewById(R.id.itemProgress);
        itemimage = itemView.findViewById(R.id.itemImage);
        name = itemView.findViewById(R.id.itemName);
        size = itemView.findViewById(R.id.itemShareSize);
        percentage = itemView.findViewById(R.id.percentageItem);
    }

    public void bind(ItemModel model, int progress) {
        itemimage.setImageDrawable(model.getIcon());
        name.setText(model.getName());
        progressBar.setProgress(0, true);
        size.setText(model.getSize());
        percentage.setText(progress + "%");
        progressBar.setProgress(progress);

    }
}
