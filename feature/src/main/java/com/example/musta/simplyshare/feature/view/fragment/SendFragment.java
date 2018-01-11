package com.example.musta.simplyshare.feature.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.DeviceModelMapper;
import com.example.musta.simplyshare.feature.presenter.DeviceListPresenter;
import com.example.musta.simplyshare.feature.view.DeviceListView;

import java.util.ArrayList;
import java.util.List;

import data.musta.it.apiit.com.entity.mapper.DeviceEntityMapper;
import data.musta.it.apiit.com.repository.DeviceDataRepository;
import data.musta.it.apiit.com.repository.datasource.device.DeviceWifiP2PRetriever;
import data.musta.it.apiit.com.repository.datasource.device.WifiActivity;
import model.musta.it.apiit.com.interactor.GetDeviceList;
import model.musta.it.apiit.com.repository.DevicesDiscovered;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment implements DeviceListView, WifiActivity, WifiP2pManager.ChannelListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private RadarView mRadarView = null;

    private DeviceListPresenter presenter;
    private GetDeviceList getDeviceList;
    private DeviceModelMapper deviceModelMapper;
    private DeviceDataRepository deviceDataRepository;
    private DeviceEntityMapper deviceEntityMapper;
    private DeviceWifiP2PRetriever deviceWifiP2PRetriever;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    private boolean isWifiP2pEnabled = false;
    private BroadcastReceiver receiver;
    private final IntentFilter intentFilter = new IntentFilter();

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
        mRadarView = view.findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
        mRadarView.startAnimation();

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        deviceModelMapper = new DeviceModelMapper();
        deviceEntityMapper = new DeviceEntityMapper();
        deviceWifiP2PRetriever = new DeviceWifiP2PRetriever(this);
        manager = (WifiP2pManager) getContext().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getContext(), getContext().getMainLooper(), this);
        deviceDataRepository = new DeviceDataRepository(deviceWifiP2PRetriever, deviceEntityMapper, manager, channel, this);
        getDeviceList = new GetDeviceList(deviceDataRepository);
        presenter = new DeviceListPresenter(getDeviceList,deviceModelMapper,this);
        presenter.initialize();

        return inflater.inflate(R.layout.fragment_send, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        receiver = (BroadcastReceiver) getDeviceList.create();
        getContext().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(receiver);
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
    public void update() {
        presenter.update();
    }

    @Override
    public void showLoading() {
        Toast.makeText(getContext(), "Start getting devices", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(getContext(), "Finish gettings devices", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMessage) {
        Log.d(TAG, "showError: "+errorMessage);
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void renderDeviceList(List<DeviceModel> deviceModels) {
        Log.d(TAG, "renderDeviceList: "+deviceModels.size()+" first: "+deviceModels.get(0).getName());
    }

    @Override
    public DeviceModel getSelectedDevice() {
        return null;
    }

    @Override
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    public void resetData() {

    }

    @Override
    public void onChannelDisconnected() {

    }
}
