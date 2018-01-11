package model.musta.it.apiit.com.interactor;

import java.util.List;

import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.repository.DeviceRepository;
import model.musta.it.apiit.com.repository.DevicesDiscovered;
import model.musta.it.apiit.com.repository.WifiBroadcastReceiver;

/**
 * Created by musta on 05-Jan-18.
 */

public class GetDeviceList extends UseCase<List<Device>, Void> {

    private DeviceRepository deviceRepository;

    public GetDeviceList(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    protected List<Device> buildUseCase(Void aVoid) {
        return deviceRepository.devices();
    }

    public WifiBroadcastReceiver create(){
        return deviceRepository.create();
    }

    public void start(DevicesDiscovered devicesDiscovered){
        deviceRepository.start(devicesDiscovered);
    }
}
