package data.musta.it.apiit.com.entity.mapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.util.Collections;
import model.musta.it.apiit.com.model.Item;
/**
 * Created by musta on 23-Dec-17.
 */

public class ItemEntityMapper {

    public ItemEntityMapper() {

    }

    public Item transform(ItemEntity itemEntity){
        Item item = null;
        if(itemEntity != null){
            item = new Item(itemEntity.getId(), itemEntity.getName(), Double.parseDouble(itemEntity.getSize()), itemEntity.getData(), itemEntity.getPath(), itemEntity.getType());
        }
        return item;
    }

    public ItemEntity revert(Item item) {
        ItemEntity itemEntity = null;
        File file = new File(item.getPath());
        if (item != null && !file.isDirectory())
            itemEntity = new ItemEntity(item.getId(), item.getName(), Double.toString(item.getSize()), new Date(file.lastModified()).toString(),
                    item.getPath().split(".")[item.getPath().split(".").length - 1],
                    item.getData(), item.getPath(), item.getType());
        return itemEntity;
    }

    public List<ItemEntity> revertList(List<Item> items) {

        List<ItemEntity> itemEnities = new ArrayList<>();
        if (items != null && !items.isEmpty())
            itemEnities = Collections.convertList(items, this::revert);
        return itemEnities;
    }

    public List<Item> transformList(List<ItemEntity> itemEntities){
        List<Item> items = new ArrayList<>();
        if(itemEntities != null)
            items = Collections.convertList(itemEntities, this::transform);
        return items;
    }
}
