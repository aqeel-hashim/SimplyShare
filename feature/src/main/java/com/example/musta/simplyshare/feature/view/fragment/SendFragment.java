package com.example.musta.simplyshare.feature.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.DeviceModel;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.DeviceModelMapper;
import com.example.musta.simplyshare.feature.presenter.DeviceViewPresenter;
import com.example.musta.simplyshare.feature.presenter.TransferViewPresenter;
import com.example.musta.simplyshare.feature.view.adapter.ItemProgressAdapter;
import com.example.musta.simplyshare.feature.view.adapter.RadarDeviceAdapter;
import com.example.musta.simplyshare.feature.view.adapter.viewholder.RadarDeviceViewholder;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import data.musta.it.apiit.com.repository.connection.WifiP2pTransferManager;
import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment implements ConnectionListner, OnPeersChangedListner, WifiP2PEnbleListner, RadarDeviceViewholder.DeviceConnectClickListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";
    private static final String TAG = SendFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private boolean wifienabled;

    private DeviceViewPresenter presenter;
    private DeviceManager deviceManager;

    private RadarDeviceAdapter adapter;
    private RecyclerView recyclerView;

    private Device device;

    private boolean connected = false;
    private ProgressFragment progressFragment;

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

        /*final RandomTextView randomTextView = (RandomTextView) view.findViewById(
                R.id.random_textview);

        new Handler().postDelayed(() -> {
            randomTextView.addKeyWord("Test");
            randomTextView.addKeyWord("Test 2");
            randomTextView.show();
        }, 2000);
        randomTextView.show();*/

        adapter = new RadarDeviceAdapter(new LinkedList<>(), this);
        recyclerView = view.findViewById(R.id.rvItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        AVLoadingIndicatorView indicatorView = view.findViewById(R.id.indicatorView);
        indicatorView.smoothToShow();

        ItemProgressAdapter adapter = new ItemProgressAdapter(new ArrayList<>(itemModels), getContext(), new TransferViewPresenter(new WifiP2pTransferManager(getContext())), false);
        progressFragment = ProgressFragment.newInstance(new ArrayList<>(itemModels), false, adapter);
        deviceManager = new DeviceWifiPP2PManager(getContext(), this, this, adapter);
        presenter = new DeviceViewPresenter(deviceManager);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.initialize();
        presenter.resume(this);

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
        if (enable) {

        }
        Log.d(TAG, "enable: "+enable);
    }

    @Override
    public void updateDeviceList(List<Device> devices) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.updateList(new LinkedList<>(new DeviceModelMapper().transformList(devices)));
        Log.d(TAG, "updateDeviceList: "+devices.size());
    }

    @Override
    public void connected() {
        if (!connected) {

            connected = true;


            View view = getActivity() != null ? getActivity().findViewById(R.id.container) : null;
            if (view != null) view.setVisibility(View.VISIBLE);

            FragmentManager fm = getActivity() != null ? getActivity().getSupportFragmentManager() : null;
            if (fm != null)
                fm.beginTransaction().replace(R.id.container, progressFragment, "ProgressFragment").addToBackStack(null).commit();
        }
    }

    @Override
    public void disconnected() {
        Log.d(TAG, "disconnected: ");
        if (connected) {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
            connected = false;
        }
    }

    @Override
    public void updateCurrentDevice(Device device) {
        Log.d(TAG, "updateCurrentDevice: "+device.toString());
        this.device = device;
    }

    @Override
    public void connect(DeviceModel device) {
        Log.d(TAG, "connect: CLICK PASSED ---> "+device.getName());
        presenter.connect(device);
        getActivity().runOnUiThread(() -> Log.d(TAG, "connect: " + device.getIp()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();

    }
}
