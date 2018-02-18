package com.example.musta.simplyshare.feature.model.mapper;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.DeviceModel;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.util.Collections;
import model.musta.it.apiit.com.model.Device;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceModelMapper {

    public DeviceModel transform(Device device){
        return new DeviceModel(device.getIpAddress(), device.getMacAddress(), device.getName(), R.drawable.simplyshare);
    }

    public List<DeviceModel> transformList(List<Device> devices){
        List<DeviceModel> deviceModels = new ArrayList<>();
        if(devices != null)
            deviceModels = Collections.convertList(devices, this::transform);
        return deviceModels;
    }

}
