package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 28-Dec-17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageIcon;
    private TextView textName;
    private TextView textInfo;
    private TextView textViedeoTime;
    private View mainView;
    public ItemViewHolder(View v) {
        super(v);
        mainView = v;
        this.imageIcon = v.findViewById(R.id.card_image);
        this.textName = v.findViewById(R.id.card_name);
        this.textInfo = v.findViewById(R.id.card_info);
        this.textViedeoTime = v.findViewById(R.id.videotime);
    }

    public void bindSelection(ItemModel itemModel, final int index, HashMap<Integer, Boolean> selectedIndexes, RecyclerView.Adapter adapter, Context context){
        Drawable thumb;
        final int THUMBSIZE = 128;
        if (itemModel.getType() == Item.Type.VIDEO) {
            Glide.with(context).load(ThumbnailUtils.createVideoThumbnail(itemModel.getPath(),
                    MediaStore.Images.Thumbnails.MICRO_KIND)).into(imageIcon);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
            retriever.setDataSource(context, Uri.fromFile(new File(itemModel.getPath())));
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInMillisec = Long.parseLong(time);

            retriever.release();
            textViedeoTime.setVisibility(View.VISIBLE);
            DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.US);

            Date date = new Date(timeInMillisec);

            textViedeoTime.setText(df.format(date));

        } else if (itemModel.getType() == Item.Type.PICTURE)
            Glide.with(context).load(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(itemModel.getPath()),
                    THUMBSIZE, THUMBSIZE)).into(imageIcon);
        else
            imageIcon.setImageDrawable(itemModel.getIcon());


        textInfo.setText(itemModel.getSize());
        textName.setText(itemModel.getName());
        Log.d(ItemViewHolder.class.getSimpleName(), "bindSelection: " + itemModel.getName());
        if(selectedIndexes != null ) {
            if (selectedIndexes.containsKey(index) && selectedIndexes.get(index)) {
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_bright));
            } else {
                selectedIndexes.put(index, false);
                mainView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            }
        }
        mainView.setOnClickListener(v -> {
            if (selectedIndexes != null) {
                if (selectedIndexes.containsKey(index) && !selectedIndexes.get(index)) {
                    mainView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_bright));
                    adapter.notifyItemChanged(index);
                    selectedIndexes.put(index, true);
                } else {
                    mainView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                    adapter.notifyItemChanged(index);
                    selectedIndexes.put(index, false);
                }
            }
        });
    }
}
