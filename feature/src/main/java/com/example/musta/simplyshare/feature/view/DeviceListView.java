package com.example.musta.simplyshare.feature.view;

import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.model.ItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.musta.it.apiit.com.repository.datasource.device.DeviceListUpdater;

/**
 * Created by musta on 11-Jan-18.
 */

public interface DeviceListView extends DeviceListUpdater, LoadDataView {
    void renderDeviceList(List<DeviceModel> deviceModels);
    DeviceModel getSelectedDevice();
}
