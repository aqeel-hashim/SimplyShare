package model.musta.it.apiit.com.interactor;

import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 20-Jan-18.
 */

public interface ConnectionListner {
    void connected();
    void disconnected();
    void updateCurrentDevice(Device device);
}
