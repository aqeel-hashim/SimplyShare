package data.musta.it.apiit.com.cache;

import java.util.List;

import data.musta.it.apiit.com.entity.ItemEntity;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 28-Dec-17.
 */

public interface ItemCache {
    boolean isCached(Item.Type provider);
    boolean isExpired();
    List<ItemEntity> get(Item.Type provider);
    void put(Item.Type provider, List<ItemEntity> itemEntities);
}
