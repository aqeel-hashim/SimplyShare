package com.example.musta.simplyshare.feature.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.presenter.TransferViewPresenter;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.ItemProgressViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class ItemProgressAdapter extends RecyclerView.Adapter<ItemProgressViewHolder> implements TransferProgressListener {

    private List<ItemModel> itemModels;
    private SparseIntArray progressForIndex;
    private Context context;
    private static final String TAG = ItemProgressAdapter.class.getSimpleName();
    private int currentIndex = -1;
    private TransferViewPresenter presenter;
    private boolean isReceive;

    public ItemProgressAdapter(List<ItemModel> itemModels, Context context, TransferViewPresenter presenter, boolean isReceive) {
        this.itemModels = itemModels;
        this.context = context;
        this.presenter = presenter;
        this.isReceive = isReceive;
        progressForIndex = new SparseIntArray();
    }

    @Override
    public ItemProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_element, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemProgressViewHolder holder, int position) {
        holder.bind(itemModels.get(position), progressForIndex.get(position));
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public TransferViewPresenter getPresenter() {
        return presenter;
    }

    public void initialize() {
        if (currentIndex == -1) {
            presenter.send(new ItemModelMapper(context).revert(itemModels.get(0)), this);
            currentIndex = 0;
        }
    }

    @Override
    public void updateProgress(int progress, Item file) {
        if (file == null) return;
        int index = -1;
        //Log.d(TAG, "updateProgress: for file "+file.getName());
        if (itemModels != null && itemModels.size() > 0)
            index = Collections.binarySearch(itemModels, new ItemModelMapper(context).transform(file), Comparator.comparing(ItemModel::getName));
        if (index == -1) {
            itemModels.add(new ItemModelMapper(context).transform(file));
            index = Collections.binarySearch(itemModels, new ItemModelMapper(context).transform(file), Comparator.comparing(ItemModel::getName));

        }
        progressForIndex.append(index, progress);
        notifyDataSetChanged();
    }

    @Override
    public void endProgress(Item file) {
        if (file == null) return;
        int index = Collections.binarySearch(itemModels, new ItemModelMapper(context).transform(file), Comparator.comparing(ItemModel::getName));

        progressForIndex.append(index, 100);

        currentIndex++;

        if (currentIndex < itemModels.size() && !isReceive)
            presenter.send(new ItemModelMapper(context).revert(itemModels.get(currentIndex)), this);

        //      if(!isReceive)
//            notifyDataSetChanged();
    }

    @Override
    public void transferFinished() {

    }
}
