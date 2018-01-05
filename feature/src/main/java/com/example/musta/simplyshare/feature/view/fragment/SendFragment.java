package com.example.musta.simplyshare.feature.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private RadarView mRadarView = null;

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
        return inflater.inflate(R.layout.fragment_send, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
