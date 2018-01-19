package com.example.musta.simplyshare.feature.view;

import com.example.musta.simplyshare.feature.model.DeviceModel;

import java.util.List;

import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * Created by musta on 20-Jan-18.
 */

public interface WifiP2PDeviceView extends LoadDataView {
    void renderDeviceList(List<DeviceModel> deviceModels);
    void connected(WifiP2pInfo info);
    void disconnect();
    void wifiEnabled(boolean enabled);
}
