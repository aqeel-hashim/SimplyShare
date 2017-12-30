package com.example.musta.simplyshare.feature.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.ItemViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by musta on 28-Dec-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<ItemModel> itemModels;
    private HashMap<Integer, Boolean> selectedIndexes;
    private Context context;

    public ItemAdapter(List<ItemModel> itemModels, HashMap<Integer, Boolean> selectedIndexes, Context context) {
        this.itemModels = itemModels;
        this.selectedIndexes = selectedIndexes;
        this.context = context;
    }

    public void setItemModels(List<ItemModel> itemModels) {
        this.itemModels = itemModels;
        this.selectedIndexes = new HashMap<>();
        notifyDataSetChanged();
    }

    public void setSelectedIndexes(HashMap<Integer, Boolean> selectedIndexes){
        this.selectedIndexes = selectedIndexes;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.card_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindSelection(itemModels.get(position),position,selectedIndexes,this,context);
    }

    public HashMap<Integer, Boolean> getSelectedIndexes() {
        return selectedIndexes;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
