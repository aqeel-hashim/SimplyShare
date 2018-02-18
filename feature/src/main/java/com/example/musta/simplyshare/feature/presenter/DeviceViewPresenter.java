package com.example.musta.simplyshare.feature.presenter;

import com.example.musta.simplyshare.feature.model.DeviceModel;

import java.io.Serializable;

import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * Created by musta on 20-Jan-18.
 */

public class DeviceViewPresenter implements Serializable {

    private DeviceManager deviceManager;

    public DeviceViewPresenter(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void resume(OnPeersChangedListner peersChangedListner) {
        deviceManager.resume();
        ((DeviceWifiPP2PManager) deviceManager).addOnPeersChangedListner(peersChangedListner);
        deviceManager.discoverPeers();
    }

    public void pause(){
        deviceManager.pause();
    }

    public void destroy(){
        deviceManager.destroy();
    }

    public void initialize(){
        deviceManager.initialize();
    }

    public void connect(DeviceModel deviceModel){
        Device device = new Device(deviceModel.getIp(), deviceModel.getName(), deviceModel.getMacAddress());
        deviceManager.connect(device);
    }

    public void setTransferListner(TransferProgressListener listner) {
        ((DeviceWifiPP2PManager) deviceManager).setListner(listner);
    }

    public void connectionHandshake(WifiP2pInfo info) {
        deviceManager.sendConnectionMessage(info);
    }

}
