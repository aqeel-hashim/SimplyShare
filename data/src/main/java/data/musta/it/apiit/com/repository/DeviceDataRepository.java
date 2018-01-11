package data.musta.it.apiit.com.repository;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.List;

import data.musta.it.apiit.com.entity.mapper.DeviceEntityMapper;
import data.musta.it.apiit.com.repository.datasource.device.DeviceWifiP2PRetriever;
import data.musta.it.apiit.com.repository.datasource.device.WiFiDirectBroadcastReceiver;
import data.musta.it.apiit.com.repository.datasource.device.WifiActivity;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.repository.DeviceRepository;
import model.musta.it.apiit.com.repository.DevicesDiscovered;
import model.musta.it.apiit.com.repository.WifiBroadcastReceiver;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceDataRepository implements DeviceRepository{
    private final String TAG = DeviceDataRepository.class.getCanonicalName();
    private DeviceWifiP2PRetriever deviceWifiP2PRetriever;
    private DeviceEntityMapper deviceEntityMapper;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiActivity activity;

    public DeviceDataRepository(DeviceWifiP2PRetriever deviceWifiP2PRetriever, DeviceEntityMapper deviceEntityMapper,
                                WifiP2pManager manager, WifiP2pManager.Channel channel,
                                WifiActivity activity) {
        this.deviceWifiP2PRetriever = deviceWifiP2PRetriever;
        this.deviceEntityMapper = deviceEntityMapper;
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void start(DevicesDiscovered devicesDiscovered) {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener(){
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
                devicesDiscovered.Success();
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Override
    public List<Device> devices() {
        return deviceEntityMapper.transformList(deviceWifiP2PRetriever.devices());
    }

    @Override
    public WifiBroadcastReceiver create() {
        return new WiFiDirectBroadcastReceiver(manager,channel,activity,deviceWifiP2PRetriever);
    }

}
