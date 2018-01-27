package com.example.musta.simplyshare.feature.presenter;

import com.example.musta.simplyshare.feature.model.DeviceModel;

import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * Created by musta on 20-Jan-18.
 */

public class DeviceViewPresenter{

    private DeviceManager deviceManager;

    public DeviceViewPresenter(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void resume(){
        deviceManager.resume();
        deviceManager.discoverPeers();
    }

    public void pause(){
        deviceManager.pause();
    }

    public void destroy(){
        deviceManager = null;
    }

    public void initialize(){
        deviceManager.initialize();
    }

    public void connect(DeviceModel deviceModel){
        Device device = new Device(deviceModel.getId().split("@")[0].trim(), deviceModel.getName(), deviceModel.getId().split("@")[1].trim());
        deviceManager.connect(device);
    }

    public void connectionHandshake(WifiP2pInfo info) {

    }

}
