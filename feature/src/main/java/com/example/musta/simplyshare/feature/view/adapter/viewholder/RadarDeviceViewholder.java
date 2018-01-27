package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.view.adapter.RadarDeviceAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by musta on 24-Jan-18.
 */

public class RadarDeviceViewholder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private int image_url;
    private TextView name;
    private View mainView;
    private DeviceConnectClickListner listner;
    private static final String TAG = RadarDeviceAdapter.class.getSimpleName();

    public RadarDeviceViewholder(View itemView, DeviceConnectClickListner listner) {
        super(itemView);
        mainView = itemView;
        imageView = (ImageView) itemView.findViewById(R.id.img_view);
        name = (TextView) itemView.findViewById(R.id.deviceName);
        this.listner = listner;
    }

    public void setImageUrl(DeviceModel deviceModel){
        this.image_url = deviceModel.getImage();
        Picasso.with(imageView.getContext()).load(image_url).into(imageView);
        name.setText(deviceModel.getName());
        mainView.setOnClickListener(v -> {
            Log.d(TAG, "setImageUrl: CLICKED ---> "+deviceModel.getName());
            listner.connect(deviceModel);
        });
    }

    public interface DeviceConnectClickListner{
        void connect(DeviceModel device);
    }
}
