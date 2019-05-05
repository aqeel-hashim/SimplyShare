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
                    getExtension(item.getPath()),
                    new byte[1], item.getPath(), item.getType());
        return itemEntity;
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
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
