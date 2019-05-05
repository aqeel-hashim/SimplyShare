package com.example.musta.simplyshare.feature.model.mapper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.musta.it.apiit.com.util.Collections;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemModelMapper {
    private Context context;
    public ItemModelMapper(Context context){
        this.context = context;
    }

    public ItemModel transform(Item item){
        ItemModel itemModel = null;
        File file = null;
        if(item != null) {
            file = new File(item.getPath());
            Drawable icon = null;
            switch(item.getType()){
                case APPLICATION:
                    try {
                        icon = context.getPackageManager().getApplicationIcon(item.getId());
                    } catch (PackageManager.NameNotFoundException e) {
                        //e.printStackTrace();

                    }
                    break;
                case VIDEO:
                case PICTURE:
                    icon = ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
                    break;
                case FILE:
                    icon = ContextCompat.getDrawable(context, R.mipmap.ic_file);
                    Log.d(ItemModelMapper.class.getSimpleName(), "transform: item name: " + item.getName() + "\nFile name: " + file.getName());
                    break;
                case MUSIC:
                    icon = ContextCompat.getDrawable(context, R.mipmap.ic_music);
                    break;
            }

            itemModel = new ItemModel(item.getName() != null && !TextUtils.isEmpty(item.getName()) ? item.getName().length() > 54 ? item.getName().substring(0, 50) + " ..." : item.getName() :
                    file.getName(),
                    item.getPath(),
                    file.length() > 1048576 ? String.format(Locale.ENGLISH, "%.2f MB",
                            file.exists() ? ((double) file.length()) / 1024.0f / 1024.0f : 0.0) :
                            String.format(Locale.ENGLISH, "%.2f KB", file.exists() ?
                                    ((double) file.length()) / 1024.0f : 0.0),
                    icon,
                    item.getType());
        }
        return itemModel;
    }

    public Item revert(ItemModel itemModel) {
        Item item = null;
        File file = null;
        if (itemModel != null) {
            file = new File(itemModel.getPath());
            String size = itemModel.getSize();
            byte[] data = new byte[(int) file.length()];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(data, 0, (int) file.length());
                buf.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            item = new Item("UNKNOW", itemModel.getName(), Double.parseDouble(Long.toString(file.length())), data, itemModel.getPath(), itemModel.getType());

        }
        return item;
    }

    public List<Item> revertList(List<ItemModel> itemModels) {
        List<Item> items = new ArrayList<>();
        if (itemModels != null && !itemModels.isEmpty())
            items = Collections.convertList(itemModels, this::revert);
        return items;
    }

    public List<ItemModel> transformList(List<Item> items){
        List<ItemModel> itemModels = new ArrayList<>();
        if(items != null && !items.isEmpty())
            itemModels = Collections.convertList(items, this::transform);
        return itemModels;
    }
}
