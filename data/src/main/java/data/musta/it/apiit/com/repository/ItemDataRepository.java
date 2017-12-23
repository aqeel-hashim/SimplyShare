package data.musta.it.apiit.com.repository;

import java.util.List;

import data.musta.it.apiit.com.entity.mapper.ItemEntityMapper;
import data.musta.it.apiit.com.repository.datasource.ItemDataStoreFactory;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemDataRepository {
    private final ItemDataStoreFactory itemDataStoreFactory;
    private final ItemEntityMapper itemEntityMapper;

    public ItemDataRepository(ItemDataStoreFactory itemDataStoreFactory, ItemEntityMapper itemEntityMapper) {
        this.itemDataStoreFactory = itemDataStoreFactory;
        this.itemEntityMapper = itemEntityMapper;
    }

    public List<Item> items(Item.Type provider){
        return itemEntityMapper.transformList(itemDataStoreFactory.create().items(provider));
    }
}
