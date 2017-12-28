package com.example.musta.simplyshare.feature.view;

import com.example.musta.simplyshare.feature.model.ItemModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by musta on 27-Dec-17.
 */

public interface ItemListView extends LoadDataView {
    void renderItemList(List<ItemModel> itemModels);
    void renderSelectedItemList(HashMap<Integer, Boolean> selectedIndexes);
    HashMap<Integer, Boolean> saveSelectedIndexes();
}
