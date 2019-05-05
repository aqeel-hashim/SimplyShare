package model.musta.it.apiit.com.repository;

import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;

/**
 * Created by musta on 19-Jan-18.
 */

public interface DeviceManager {
    void initialize();
    void discoverPeers();
    void resume();
    void pause();

    void destroy();
    void connect(Device device);
    void sendConnectionMessage(WifiP2pInfo info);
}
