package data.musta.it.apiit.com.entity;

import java.io.Serializable;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public class WifiP2pDevice implements Serializable {
    public String deviceName;
    public String deviceAddress;

    public WifiP2pDevice(String deviceName, String macAddress) {
        this.deviceName = deviceName;
        this.deviceAddress = macAddress;
    }

    public WifiP2pDevice() {
    }

    public WifiP2pDevice Clone(android.net.wifi.p2p.WifiP2pDevice device) {
        deviceName = device.deviceName;
        deviceAddress = device.deviceAddress;
        return this;
    }
}
