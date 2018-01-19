package model.musta.it.apiit.com.interactor;

import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;

/**
 * Created by musta on 20-Jan-18.
 */

public interface ConnectionListner {
    void connected(WifiP2pInfo info);
    void disconnected();
    void updateCurrentDevice(Device device);
}
