package com.example.musta.simplyshare.feature.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.RadarDeviceViewholder;

import java.util.LinkedList;

/**
 * Created by musta on 24-Jan-18.
 */

public class RadarDeviceAdapter extends RecyclerView.Adapter<RadarDeviceViewholder> {

    private LinkedList<DeviceModel> images;
    private RadarDeviceViewholder.DeviceConnectClickListner listner;

    public RadarDeviceAdapter(LinkedList<DeviceModel> images, RadarDeviceViewholder.DeviceConnectClickListner listner) {
        this.images = images;
        this.listner = listner;
    }

    @Override
    public RadarDeviceViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadarDeviceViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device_hexagon_element, parent, false), listner);
    }

    @Override
    public void onBindViewHolder(RadarDeviceViewholder holder, int position) {
        if (images == null || images.size() == 0) {
            return;
        }
        holder.setImageUrl(images.get(position));
    }

    @Override
    public int getItemCount() {
        if (images == null) {
            return 0;
        }

        return images.size();
    }

    public void updateList(LinkedList<DeviceModel> itemsList) {
        this.images = itemsList;
        notifyDataSetChanged();
    }

    public void addToList(LinkedList<DeviceModel> itemsList) {
        this.images.addAll(itemsList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.images.clear();
        notifyDataSetChanged();
    }
}
