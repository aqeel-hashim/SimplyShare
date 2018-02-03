package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.presenter.TransferViewPresenter;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class ItemProgressViewHolder extends RecyclerView.ViewHolder {

    private transient ProgressBar progressBar;
    private transient ImageView itemimage;
    private transient TextView name;
    private transient View mainView;

    public ItemProgressViewHolder(View itemView) {
        super(itemView);
        mainView = itemView;
        progressBar = itemView.findViewById(R.id.itemProgress);
        itemimage = itemView.findViewById(R.id.itemImage);
        name = itemView.findViewById(R.id.itemName);
    }

    public void bind(ItemModel model, TransferViewPresenter presenter) {
        itemimage.setImageDrawable(model.getIcon());
        name.setText(model.getName());
        progressBar.setProgress(0, true);
        presenter.send(new ItemModelMapper(mainView.getContext()).revert(model), new ProgressUpdator(progressBar));
        float deviceScreenHeight = mainView.getContext().getResources().getDisplayMetrics().heightPixels;

    }
}
