package com.example.musta.simplyshare.feature.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.presenter.DeviceViewPresenter;
import com.guo.duoduo.randomtextview.RandomTextView;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment implements ConnectionListner, OnPeersChangedListner, WifiP2PEnbleListner{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";
    private static final String TAG = SendFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private boolean wifienabled;

    private DeviceViewPresenter presenter;
    private DeviceManager deviceManager;

    public SendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemModels List of Items to send.
     * @return A new instance of fragment SendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendFragment newInstance(ArrayList<ItemModel> itemModels) {
        SendFragment fragment = new SendFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM_LIST, itemModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemModels = getArguments().getParcelableArrayList(ARG_ITEM_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        final RandomTextView randomTextView = (RandomTextView) view.findViewById(
                R.id.random_textview);

        new Handler().postDelayed(() -> {
            randomTextView.addKeyWord("Test");
            randomTextView.addKeyWord("Test 2");
            randomTextView.show();
        }, 2000);
        randomTextView.show();

        deviceManager = new DeviceWifiPP2PManager(getContext(), this, this);
        presenter = new DeviceViewPresenter(deviceManager);
        presenter.initialize();
        return inflater.inflate(R.layout.fragment_send, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void enable(boolean enable) {
        this.wifienabled = enable;
        Log.d(TAG, "enable: "+enable);
    }

    @Override
    public void updateDeviceList(List<Device> devices) {
        Log.d(TAG, "updateDeviceList: size: "+devices.size()+"\nfirst: "+devices.get(0).toString());
    }

    @Override
    public void connected(WifiP2pInfo info) {
        Log.d(TAG, "connected: "+info.groupOwnerAddress);
    }

    @Override
    public void disconnected() {
        Log.d(TAG, "disconnected: ");
    }

    @Override
    public void updateCurrentDevice(Device device) {
        Log.d(TAG, "updateCurrentDevice: "+device.toString());
    }
}
