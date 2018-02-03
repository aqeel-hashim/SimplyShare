package com.example.musta.simplyshare.feature.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.presenter.DeviceViewPresenter;
import com.skyfishjy.library.RippleBackground;

import java.util.List;

import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import model.musta.it.apiit.com.interactor.ConnectionListner;
import model.musta.it.apiit.com.interactor.OnPeersChangedListner;
import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.interactor.WifiP2PEnbleListner;
import model.musta.it.apiit.com.model.Device;
import model.musta.it.apiit.com.model.WifiP2pInfo;
import model.musta.it.apiit.com.repository.DeviceManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveFragment extends Fragment implements ConnectionListner, OnPeersChangedListner, WifiP2PEnbleListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = ReceiveFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView nameText;
    private TextView broadcastText;

    private DeviceViewPresenter presenter;
    private DeviceManager deviceManager;

    private boolean connected = false;
    public ReceiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiveFragment newInstance(String param1, String param2) {
        ReceiveFragment fragment = new ReceiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        final RippleBackground rippleBackground = view.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        nameText = view.findViewById(R.id.currentDeviceName);
        broadcastText = view.findViewById(R.id.broadcasting);
        broadcastText.setText("Broadcasting...");
        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        deviceManager = new DeviceWifiPP2PManager(getContext(), this, this, new TransferProgressListener() {
            @Override
            public void updateProgress(int progress) {
                Log.d(TAG, " file progress update: " + progress);
            }

            @Override
            public void endProgress() {
                Log.d(TAG, " file progress end: ");
            }

            @Override
            public void transferFinished() {
                Log.d(TAG, " file progress finish: ");
            }
        });
        presenter = new DeviceViewPresenter(deviceManager);
        presenter.initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        ((DeviceWifiPP2PManager) deviceManager).addOnPeersChangedListner(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void enable(boolean enable) {
        if (enable) {

        }
    }

    @Override
    public void updateDeviceList(List<Device> devices) {

    }

    @Override
    public void connected(WifiP2pInfo info) {
        if (!connected) {
            presenter.connectionHandshake(info);
            broadcastText.setText("Connected");
            connected = true;
        }
    }

    @Override
    public void disconnected() {
        broadcastText.setText("Broadcasting...");
        if (connected) {
            presenter.pause();
            presenter.destroy();
            presenter.initialize();
            presenter.resume();
            connected = false;
        }
    }

    @Override
    public void updateCurrentDevice(Device device) {
        nameText.setText("My Device: " + device.getName());
    }


}
