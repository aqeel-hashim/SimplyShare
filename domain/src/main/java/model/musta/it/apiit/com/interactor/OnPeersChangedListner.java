package model.musta.it.apiit.com.interactor;

import java.util.List;

import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 19-Jan-18.
 */

public interface OnPeersChangedListner {
    void updateDeviceList(List<Device> devices);
}
