package model.musta.it.apiit.com.repository;

import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 19-Jan-18.
 */

public interface DeviceManager {
    void initialize();
    void discoverPeers();
    void resume();
    void pause();
    void connect(Device device);
}
