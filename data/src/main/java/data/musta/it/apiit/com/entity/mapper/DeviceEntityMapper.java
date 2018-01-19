package data.musta.it.apiit.com.entity.mapper;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

import data.musta.it.apiit.com.entity.DeviceEntity;
import data.musta.it.apiit.com.util.Collections;
import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 05-Jan-18.
 */

public class DeviceEntityMapper {
    public DeviceEntityMapper() {

    }

    public static Device transformFromWifiP2P(WifiP2pDevice device){
        return new Device("", device.deviceName, device.deviceAddress);
    }

    public Device transform(DeviceEntity wifidevice){
        Device device = null;
        if(wifidevice != null){
            device = new Device(wifidevice.getIpAddress(), wifidevice.getDevice().deviceName, wifidevice.getDevice().deviceAddress);
        }

        return device;
    }

    public List<Device> transformList(List<DeviceEntity> deviceEntities){
        List<Device> devices = null;
        if(deviceEntities != null)
            devices = Collections.convertList(deviceEntities, this::transform);
        return devices;
    }
}
