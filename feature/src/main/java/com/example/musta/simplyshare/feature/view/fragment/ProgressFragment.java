package com.example.musta.simplyshare.feature.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.presenter.TransferViewPresenter;
import com.example.musta.simplyshare.feature.view.adapter.ItemProgressAdapter;

import java.util.ArrayList;
import java.util.List;

import model.musta.it.apiit.com.interactor.TransferProgressListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM_LIST = "itemModelList";
    private static final String ARG_RECEIVER = "IS_RECEIVER";

    private static final String TAG = SendFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private List<ItemModel> itemModels;
    private boolean isReceive;
    private TransferViewPresenter presenter;
    private ItemProgressAdapter adapter;


    public ProgressFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemModels Items to Send.
     * @return A new instance of fragment ProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(ArrayList<ItemModel> itemModels, boolean isReceive, ItemProgressAdapter adapter) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM_LIST, itemModels);
        args.putBoolean(ARG_RECEIVER, isReceive);
        fragment.setArguments(args);
        fragment.adapter = adapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemModels = getArguments().getParcelableArrayList(ARG_ITEM_LIST);
            isReceive = getArguments().getBoolean(ARG_RECEIVER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.itemProgressList);

        presenter = adapter.getPresenter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        TextView title = view.findViewById(R.id.titleText);
        if (isReceive)
            title.setText("Receiving Files...");
        else {
            title.setText("Sending Files...");
            adapter.initialize();
        }


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unregisterReceivers();
    }

    public TransferProgressListener getAdapter() {
        return adapter;
    }
}
