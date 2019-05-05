package data.musta.it.apiit.com.repository.datasource.item;

import android.content.Context;

import data.musta.it.apiit.com.cache.ItemCache;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemDataStoreFactory {
    private Context context;
    private ItemCache itemCache;
    public ItemDataStoreFactory(Context context, ItemCache itemCache){
        this.context = context;
        this.itemCache = itemCache;
    }

    public ItemDataSource create(Item.Type provder){
        if(!itemCache.isExpired() && itemCache.isCached(provder))
            return new ItemLocalDataStore(itemCache);
        else
            return new ItemCursorDataStore(context, itemCache);
    }
}
