package model.musta.it.apiit.com.interactor;

import model.musta.it.apiit.com.repository.DeviceNotifier;

/**
 * Created by musta on 05-Jan-18.
 */

public class DeviceNotify {
    private DeviceNotifier deviceNotifier;

    public DeviceNotify(DeviceNotifier deviceNotifier) {
        this.deviceNotifier = deviceNotifier;
    }

    public void beginNotify(){
        deviceNotifier.start();
    }
}
