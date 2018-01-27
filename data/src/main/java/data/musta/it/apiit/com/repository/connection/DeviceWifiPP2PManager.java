package data.musta.it.apiit.com.repository.connection;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.musta.it.apiit.com.R;
import data.musta.it.apiit.com.entity.DeviceEntity;
import data.musta.it.apiit.com.repository.connection.transfer.FileServerAsyncTask;
import data.musta.it.apiit.com.repository.connection.transfer.FileTransferService;
import data.musta.it.apiit.com.repository.connection.transfer.FirstConnectionMessage;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

import static data.musta.it.apiit.com.cache.ItemCacheImpl.cacheName;

/**
 * Created by musta on 20-Jan-18.
 */

public class DeviceWifiPP2PManager implements DeviceManager, OnPeersChangedListner, WiFiDirectBroadcastReceiver.CurrentDeviceUpdateListner {

    Context context;
    WifiP2pManager manager;
    WifiP2pManager.Channel mChannel;
    private WiFiDirectBroadcastReceiver receiver;
    private android.content.IntentFilter intentFilters;
    private List<Device> devices;
    private WifiP2PEnbleListner wifiP2PEnbleListner;
    private ConnectionListner connectionListner;

    private DeviceEntity currentDevice;

    private static final String TAG = DeviceWifiPP2PManager.class.getSimpleName();
    public static boolean ClientCheck = false;

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
                Log.d(TAG, "onSuccess: Discover");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: Discover reason: "+reason);
            }
        });
    }

    @Override
    public void resume() {
        receiver = new WiFiDirectBroadcastReceiver(manager, mChannel, wifiP2PEnbleListner, connectionListner, this);
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
//        boolean found = false;
//        for(Device deviceInner : devices){
//            if(!device.getMacAddress().equals(deviceInner.getMacAddress())) continue;
//            found = true;
//        }
//
//        if(!found) return;

        Log.d(TAG, "connect: Name ---> "+device.getName()+"\nMAC ---> "+device.getMacAddress());

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.getMacAddress();
        config.wps.setup = WpsInfo.PBC;

        manager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: connect");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: connect reason: "+reason);
            }
        });
    }

    @Override
    public void sendConnectionMessage(WifiP2pInfo info) {
        try {
            String groupOwner = info.groupOwnerAddress.getHostAddress();

            if (groupOwner != null && !TextUtils.isEmpty(groupOwner))
                SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)).put(context.getString(R.string.pref_GroupOwnerAddress), groupOwner);

            if (info.groupFormed && info.isGroupOwner) {

                SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)).put(context.getString(R.string.pref_ServerBoolean), "true");
                FileServerAsyncTask fileServerAsyncTask = new FileServerAsyncTask(context, FileTransferService.PORT);
                fileServerAsyncTask.execute();

            } else {
                if (!ClientCheck) {
                    FirstConnectionMessage firstConnectionMessage = new FirstConnectionMessage(info, context, currentDevice);
                    firstConnectionMessage.execute();
                }

                FileServerAsyncTask fileServerAsyncTask = new FileServerAsyncTask(context, FileTransferService.PORT);
                fileServerAsyncTask.execute();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDeviceList(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public void update(data.musta.it.apiit.com.entity.WifiP2pDevice entity) {
        String ip = getIPAddress(true);
        currentDevice = new DeviceEntity(ip, entity);
    }


    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }



}
