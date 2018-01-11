package com.example.musta.simplyshare.feature.presenter;

import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.model.mapper.DeviceModelMapper;
import com.example.musta.simplyshare.feature.view.DeviceListView;

import java.util.List;

import model.musta.it.apiit.com.interactor.GetDeviceList;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.Item;
import model.musta.it.apiit.com.repository.DevicesDiscovered;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceListPresenter implements Presenter {

    private final GetDeviceList getDeviceList;
    private final DeviceModelMapper deviceModelMapper;
    private DeviceListView deviceListView;

    public DeviceListPresenter(GetDeviceList getDeviceList, DeviceModelMapper deviceModelMapper, DeviceListView deviceListView) {
        this.getDeviceList = getDeviceList;
        this.deviceModelMapper = deviceModelMapper;
        this.deviceListView = deviceListView;
    }

    @Override
    public void resume(Item.Type provider) {

    }

    @Override
    public void pause(Item.Type provider) {

    }

    @Override
    public void destroy(Item.Type provider) {

    }

    public void initialize(){
        this.loadDeviceList();
    }

    private void loadDeviceList() {
        this.showViewLoading();
        this.getDeviceList();
    }

    private void showViewLoading() {
        this.deviceListView.showLoading();
    }

    private void hideViewLoading() {
        this.deviceListView.hideLoading();
    }

    private void showErrorMessage(String errorMessage) {
        this.deviceListView.showError(errorMessage);
    }

    private void showItemListInView(List<Device> townships) {
        final List<DeviceModel> itemModelList =
                this.deviceModelMapper.transformList(townships);
        if(itemModelList == null || itemModelList.isEmpty()){
            showErrorMessage("Item List is Empty Please Rectify");
            return;
        }else{
            hideViewLoading();
        }
        this.deviceListView.renderDeviceList(itemModelList);
    }

    public void update(){
        this.getDeviceList.start(() -> {
            showItemListInView(DeviceListPresenter.this.getDeviceList.execute(null));
        });
    }

    private void getDeviceList() {
        this.getDeviceList.start(() -> {
            showItemListInView(DeviceListPresenter.this.getDeviceList.execute(null));
        });

    }
}
