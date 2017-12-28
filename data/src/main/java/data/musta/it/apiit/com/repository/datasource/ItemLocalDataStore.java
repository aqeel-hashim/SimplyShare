package data.musta.it.apiit.com.repository.datasource;

import java.util.List;

import data.musta.it.apiit.com.cache.ItemCache;
import data.musta.it.apiit.com.entity.ItemEntity;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 28-Dec-17.
 */

public class ItemLocalDataStore implements ItemDataSource {
    private ItemCache itemCache;

    public ItemLocalDataStore(ItemCache itemCache) {
        this.itemCache = itemCache;
    }

    @Override
    public List<ItemEntity> items(Item.Type provider) {
        return itemCache.get(provider);
    }
}
