package data.musta.it.apiit.com.entity.mapper;

import java.util.ArrayList;
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

    public List<Item> transformList(List<ItemEntity> itemEntities){
        List<Item> items = new ArrayList<>();
        if(itemEntities != null)
            items = Collections.convertList(itemEntities, this::transform);
        return items;
    }
}
