package com.example.musta.simplyshare.feature.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.presenter.ItemListPresenter;
import com.example.musta.simplyshare.feature.view.ItemListView;
import com.example.musta.simplyshare.feature.view.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.musta.it.apiit.com.model.Item;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment implements ItemListView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ItemAdapter adapter;
    private RecyclerView recyclerView;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        recyclerView = view.findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(new ArrayList<>(), new HashMap<>(),getContext());
        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void renderItemList(List<ItemModel> itemModels) {
        adapter.setItemModels(itemModels);
    }

    @Override
    public void renderSelectedItemList(HashMap<Integer, Boolean> selectedIndexes) {
        adapter.setSelectedIndexes(selectedIndexes);
    }

    @Override
    public HashMap<Integer, Boolean> saveSelectedIndexes() {
        return adapter.getSelectedIndexes();
    }
}
