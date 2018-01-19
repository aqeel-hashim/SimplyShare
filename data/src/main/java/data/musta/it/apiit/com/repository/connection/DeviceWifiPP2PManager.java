package data.musta.it.apiit.com.repository.connection;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * Created by musta on 20-Jan-18.
 */

public class DeviceWifiPP2PManager implements DeviceManager, OnPeersChangedListner{

    Context context;
    WifiP2pManager manager;
    WifiP2pManager.Channel mChannel;
    private WiFiDirectBroadcastReceiver receiver;
    private android.content.IntentFilter intentFilters;
    private List<Device> devices;
    private WifiP2PEnbleListner wifiP2PEnbleListner;
    private ConnectionListner connectionListner;
    private static final String TAG = DeviceWifiPP2PManager.class.getSimpleName();

    public DeviceWifiPP2PManager(Context context, WifiP2PEnbleListner wifiP2PEnbleListner, ConnectionListner connectionListner) {
        this.context = context;
        this.wifiP2PEnbleListner = wifiP2PEnbleListner;
        this.connectionListner = connectionListner;
        devices = new ArrayList<>();
        manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        intentFilters = new IntentFilter();
        intentFilters.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilters.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilters.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilters.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    public void initialize() {
        mChannel = manager.initialize(context, context.getMainLooper(), null);
    }

    @Override
    public void discoverPeers() {
        manager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Override
    public void resume() {
        receiver = new WiFiDirectBroadcastReceiver(manager,mChannel, wifiP2PEnbleListner, connectionListner);
        context.registerReceiver(receiver, intentFilters);
    }

    public void addOnPeersChangedListner(OnPeersChangedListner onPeersChangedListner){
        receiver.addOnPeersChangedListner(onPeersChangedListner);
    }

    @Override
    public void pause() {
        context.unregisterReceiver(receiver);
    }

    @Override
    public void connect(Device device) {
        boolean found = false;
        for(Device deviceInner : devices){
            if(!device.getMacAddress().equals(deviceInner.getMacAddress())) continue;
            found = true;
        }

        if(!found) return;

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.getMacAddress();
        config.wps.setup = WpsInfo.PBC;

        manager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    @Override
    public void updateDeviceList(List<Device> devices) {
        this.devices = devices;
    }
}
