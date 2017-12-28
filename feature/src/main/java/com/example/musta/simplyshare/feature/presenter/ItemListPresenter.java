package com.example.musta.simplyshare.feature.presenter;

import android.util.SparseBooleanArray;

import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.view.ItemListView;

import java.util.HashMap;
import java.util.List;

import model.musta.it.apiit.com.interactor.GetItemList;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 27-Dec-17.
 */

public class ItemListPresenter implements ItemPresenter {

    private final GetItemList getItemList;
    private final ItemModelMapper itemModelMapper;
    private HashMap<Integer, Boolean> selectedIndexes;
    private HashMap<Item.Type, HashMap<Integer, Boolean>> selectedIndexesByType;
    private HashMap<Item.Type, ItemListView> itemListViewHashMap;

    public ItemListPresenter(GetItemList getItemList, ItemModelMapper itemModelMapper) {
        this.getItemList = getItemList;
        this.itemModelMapper = itemModelMapper;
        selectedIndexesByType = new HashMap<>();
        itemListViewHashMap = new HashMap<>();
    }

    public void addItemListViewForType(Item.Type type, ItemListView itemListView){
        itemListViewHashMap.put(type, itemListView);
    }

    @Override
    public void resume(Item.Type provider) {
        selectedIndexesByType.put(provider, itemListViewHashMap.get(provider).saveSelectedIndexes());
    }

    @Override
    public void pause(Item.Type provider) {
        selectedIndexesByType.put(provider, itemListViewHashMap.get(provider).saveSelectedIndexes());
    }

    @Override
    public void destroy(Item.Type provider) {
        selectedIndexesByType.put(provider, itemListViewHashMap.get(provider).saveSelectedIndexes());
        this.itemListViewHashMap = null;
    }

    public void initialize(Item.Type provider){
        this.loadTownshipList(provider);
    }

    private void loadTownshipList(Item.Type provider) {
        this.showViewLoading(provider);
        this.getItemList(provider);
    }

    private void showViewLoading(Item.Type provider) {
        this.itemListViewHashMap.get(provider).showLoading();
    }

    private void hideViewLoading(Item.Type provider) {
        this.itemListViewHashMap.get(provider).hideLoading();
    }

    private void showErrorMessage(Item.Type provider,String errorMessage) {
        this.itemListViewHashMap.get(provider).showError(errorMessage);
    }

    private void showItemListInView(List<Item> townships, Item.Type provider) {
        final List<ItemModel> itemModelList =
                this.itemModelMapper.transformList(townships);
        if(itemModelList == null || itemModelList.isEmpty()){
            showErrorMessage(provider,"Item List is Empty Please Rectify");
            return;
        }
        this.itemListViewHashMap.get(provider).renderItemList(itemModelList);
        this.itemListViewHashMap.get(provider).renderSelectedItemList(selectedIndexesByType.get(provider));
    }

    private void getItemList(Item.Type provider) {
        showItemListInView(this.getItemList.execute(provider), provider);
        hideViewLoading(provider);
    }
}
