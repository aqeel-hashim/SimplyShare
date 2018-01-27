package com.example.musta.simplyshare.feature.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.ItemProgressViewHolder;

import java.util.List;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class ItemProgressAdapter extends RecyclerView.Adapter<ItemProgressViewHolder> {

    List<ItemModel> itemModels;

    public ItemProgressAdapter(List<ItemModel> itemModels) {
        this.itemModels = itemModels;
    }

    @Override
    public ItemProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_element, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemProgressViewHolder holder, int position) {
        holder.bind(itemModels.get(position));
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
