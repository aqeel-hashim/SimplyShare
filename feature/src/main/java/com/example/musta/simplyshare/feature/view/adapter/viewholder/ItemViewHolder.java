package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;

import java.util.HashMap;

/**
 * Created by musta on 28-Dec-17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageIcon;
    private TextView textName;
    private TextView textInfo;
    private View mainView;
    public ItemViewHolder(View v) {
        super(v);
        mainView = v;
        this.imageIcon = v.findViewById(R.id.card_image);
        this.textName = v.findViewById(R.id.card_name);
        this.textInfo = v.findViewById(R.id.card_info);
    }

    public void bindSelection(ItemModel itemModel, final int index, HashMap<Integer, Boolean> selectedIndexes, RecyclerView.Adapter adapter, Context context){
        imageIcon.setImageDrawable(itemModel.getIcon());
        textInfo.setText(itemModel.getSize());
        textName.setText(itemModel.getName());
        if(selectedIndexes != null ) {
            if (selectedIndexes.containsKey(index) && selectedIndexes.get(index)) {
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_bright));
            } else {
                selectedIndexes.put(index, false);
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            }
        }
        mainView.setOnClickListener(v -> {
            if(selectedIndexes.containsKey(index) && !selectedIndexes.get(index)){
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_bright));
                adapter.notifyItemChanged(index);
                selectedIndexes.put(index, true);
            }else{
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                adapter.notifyItemChanged(index);
                selectedIndexes.put(index, false);
            }
        });
    }
}
