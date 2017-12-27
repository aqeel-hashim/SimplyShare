package com.example.musta.simplyshare.feature.presenter;

import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.view.ItemListView;

import java.util.List;

import model.musta.it.apiit.com.interactor.GetItemList;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 27-Dec-17.
 */

public class ItemListPresenter implements Presenter{

    private ItemListView itemListView;
    private final GetItemList getItemList;
    private final ItemModelMapper itemModelMapper;

    public ItemListPresenter(GetItemList getItemList, ItemModelMapper itemModelMapper) {
        this.getItemList = getItemList;
        this.itemModelMapper = itemModelMapper;
    }

    public void setItemListView(ItemListView itemListView) {
        this.itemListView = itemListView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.itemListView = null;
    }

    public void initialize(){
        this.loadTownshipList();
    }

    private void loadTownshipList() {
        this.showViewLoading();
        this.getItempList();
    }

    private void showViewLoading() {
        this.itemListView.showLoading();
    }

    private void hideViewLoading() {
        this.itemListView.hideLoading();
    }

    private void showErrorMessage(String errorMessage) {
        this.itemListView.showError(errorMessage);
    }

    private void showItemListInView(List<Item> townships) {
        final List<ItemModel> townshipModelList =
                this.itemModelMapper.transformList(townships);
        this.itemListView.renderItemList(townshipModelList);
    }

    private void getItempList() {
        showItemListInView(this.getItemList.execute(null));
    }
}
