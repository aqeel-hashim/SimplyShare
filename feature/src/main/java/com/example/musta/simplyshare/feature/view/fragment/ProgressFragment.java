package com.example.musta.simplyshare.feature.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.presenter.DeviceViewPresenter;
import com.example.musta.simplyshare.feature.view.adapter.ItemProgressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";
    private static final String ARG_PRESENTER = "PRESENTER";

    private static final String TAG = SendFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private DeviceViewPresenter presenter;
    private ItemProgressAdapter adapter;


    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemModels Items to Send.
     * @return A new instance of fragment ProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(ArrayList<ItemModel> itemModels, DeviceViewPresenter presenter) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM_LIST, itemModels);
        fragment.presenter = presenter;
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
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.itemProgressList);
        adapter = new ItemProgressAdapter(itemModels, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.destroy();
        adapter.pause();
    }
}
