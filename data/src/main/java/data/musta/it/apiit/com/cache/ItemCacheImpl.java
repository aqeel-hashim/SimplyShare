package data.musta.it.apiit.com.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 28-Dec-17.
 */

public class ItemCacheImpl implements ItemCache {

    private Context context;
    public final static String cacheName = ItemCacheImpl.class.getCanonicalName()+":CACHE";

    public ItemCacheImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isCached(Item.Type provider) {
        return !SharedPrefManager.getInstance(context).get(provider.toString(), "").isEmpty();
    }

    @Override
    public boolean isExpired() {
        return SharedPrefManager.getInstance(context).get("EXPIRATION", "").equals("expired");
    }

    @Override
    public List<ItemEntity> get(Item.Type provider) {
        String itemEntities = SharedPrefManager.getInstance(context).get(provider.toString(), "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemEntity>>(){}.getType();
        return gson.fromJson(itemEntities, type);
    }

    @Override
    public void put(Item.Type provider, List<ItemEntity> itemEntities) {
        Gson gson = new Gson();
        String json = gson.toJson(itemEntities);
        SharedPrefManager.getInstance(context).put(provider.toString(), json);
    }
}
