/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package data.musta.it.apiit.com.repository.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.entity.mapper.DeviceEntityMapper;
import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;


/**
 * A BroadcastReceiver that notifies of important wifi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private Channel channel;
    private WifiP2PEnbleListner wifiP2PEnbleListner;
    private List<OnPeersChangedListner> onPeersChangedListners;
    private ConnectionListner connectionListner;
    private static final String TAG = WiFiDirectBroadcastReceiver.class.getSimpleName();
    /**
     * @param manager WifiP2pManager system service
     * @param channel Wifi p2p channel
     * @param wifiP2PEnbleListner
     * @param connectionListner
     */
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, WifiP2PEnbleListner wifiP2PEnbleListner, ConnectionListner connectionListner) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.wifiP2PEnbleListner = wifiP2PEnbleListner;
        this.connectionListner = connectionListner;
        this.onPeersChangedListners = new ArrayList<>();
    }

    public void addOnPeersChangedListner(OnPeersChangedListner onPeersChangedListner){ this.onPeersChangedListners.add(onPeersChangedListner); }

    public void removeOnPeersChangedListner(OnPeersChangedListner onPeersChangedListner){ this.onPeersChangedListners.remove(onPeersChangedListner); }

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            // UI updateCurrentDevice to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                //activity.setIsWifiP2pEnabled(true);
                wifiP2PEnbleListner.enable(true);
            } else {
//                activity.setIsWifiP2pEnabled(false);
//                activity.resetData();

                wifiP2PEnbleListner.enable(true);

            }
            //Log.d(WifiActivity.TAG, "P2P state changed - " + state);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            Log.d(TAG, "onReceive: wifi peers changed");
            if (manager != null) {
                Log.d(TAG, "onReceive: manager for peers exists");
                manager.requestPeers(channel, peers -> {
                    Log.d(TAG, "on: request peers: "+peers.getDeviceList().size());
                    for(OnPeersChangedListner onPeersChangedListner : onPeersChangedListners){
                        List<Device> devices = data.musta.it.apiit.com.util.Collections.convertList(new ArrayList<>(peers.getDeviceList()), DeviceEntityMapper::transformFromWifiP2P);
                        onPeersChangedListner.updateDeviceList(devices);
                    }
                });
            }
//            Log.d(WifiActivity.TAG, "P2P peers changed");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // we are connected with the other device, request connection
                // info to find group owner IP

                manager.requestConnectionInfo(channel, info -> {
                    connectionListner.connected(new model.musta.it.apiit.com.model.WifiP2pInfo(info.groupFormed, info.isGroupOwner, info.groupOwnerAddress));
                });
            } else {
                // It's a disconnect
                //activity.resetData();
                connectionListner.disconnected();
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
//            deviceWifiP2PRetriever.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
//                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
            connectionListner.updateCurrentDevice(DeviceEntityMapper.transformFromWifiP2P((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)));

        }
    }
}
