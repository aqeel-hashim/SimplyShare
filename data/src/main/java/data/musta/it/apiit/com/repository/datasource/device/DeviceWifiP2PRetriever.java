package data.musta.it.apiit.com.repository.datasource.device;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.entity.DeviceEntity;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceWifiP2PRetriever implements WifiP2pManager.PeerListListener, WifiP2pManager.ConnectionInfoListener{

    private static final String TAG = DeviceWifiP2PRetriever.class.getCanonicalName();

    private WifiP2pDevice device;
    private List<DeviceEntity> deviceEntities;
    private WifiP2pInfo info;
    private DeviceListUpdater deviceListUpdater;

    public DeviceWifiP2PRetriever(DeviceListUpdater deviceListUpdater){
        this.deviceListUpdater = deviceListUpdater;
        deviceEntities = new ArrayList<>();
    }

    public List<DeviceEntity> devices(){
        return deviceEntities;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        for(WifiP2pDevice device : peers.getDeviceList())
            deviceEntities.add(new DeviceEntity("", device));
        Log.d(TAG, "onPeersAvailable: num devices: "+peers.getDeviceList().size());
        deviceListUpdater.update();
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        this.info = info;
    }

    void updateThisDevice(WifiP2pDevice device){
        this.device = device;
    }
}
