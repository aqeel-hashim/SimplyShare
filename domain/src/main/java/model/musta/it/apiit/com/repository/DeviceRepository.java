package model.musta.it.apiit.com.repository;

import java.util.List;

import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 05-Jan-18.
 */

public interface DeviceRepository {
    void start(DevicesDiscovered devicesDiscovered);
    List<Device> devices();
    WifiBroadcastReceiver create();
}
