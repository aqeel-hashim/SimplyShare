package com.example.musta.simplyshare.feature.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.presenter.TransferViewPresenter;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.ItemProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.repository.connection.WifiP2pTransferManager;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class ItemProgressAdapter extends RecyclerView.Adapter<ItemProgressViewHolder> {

    List<ItemModel> itemModels;
    List<TransferViewPresenter> allPresenters;
    private Context context;

    public ItemProgressAdapter(List<ItemModel> itemModels, Context context) {
        this.itemModels = itemModels;
        this.context = context;
        allPresenters = new ArrayList<>();
    }

    @Override
    public ItemProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_element, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemProgressViewHolder holder, int position) {
        TransferViewPresenter presenter = new TransferViewPresenter(new WifiP2pTransferManager(context));
        allPresenters.add(presenter);
        holder.bind(itemModels.get(position), presenter);
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public void pause() {
        for (TransferViewPresenter presenter : allPresenters) {
            presenter.unregisterReceivers();
        }
    }
}
