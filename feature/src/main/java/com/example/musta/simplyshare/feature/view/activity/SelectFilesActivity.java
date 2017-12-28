package com.example.musta.simplyshare.feature.view.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.presenter.ItemListPresenter;
import com.example.musta.simplyshare.feature.view.ItemListView;
import com.example.musta.simplyshare.feature.view.fragment.ItemFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import data.musta.it.apiit.com.cache.ItemCache;
import data.musta.it.apiit.com.cache.ItemCacheImpl;
import data.musta.it.apiit.com.entity.mapper.ItemEntityMapper;
import data.musta.it.apiit.com.repository.ItemDataRepository;
import data.musta.it.apiit.com.repository.datasource.ItemDataStoreFactory;
import model.musta.it.apiit.com.interactor.GetItemList;
import model.musta.it.apiit.com.model.Item;

public class SelectFilesActivity extends BaseActivity{


    /**
     *
     * Objects required for communicating between data,domain and presentation
     *
     ***/

    private ItemListPresenter presenter;
    private GetItemList getItemList;
    private ItemModelMapper itemModelMapper;
    private ItemDataRepository itemDataRepository;
    private ItemEntityMapper itemEntityMapper;
    private ItemDataStoreFactory itemDataStoreFactory;
    private ItemCache itemCache;

    /**
     *
     * END
     *
     * */

    @Override
    public int getContentLayout() {
        return R.layout.activity_select_files;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.mainToolbar);
    }

    @Override
    public void initComponents() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.my_tab_layout);
        final ViewPager viewPager = findViewById(R.id.my_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Apps"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Files"));
        tabLayout.addTab(tabLayout.newTab().setText("Pictures"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));

        List<ItemFragment> fragments = new Vector<>();
        fragments.add(ItemFragment.newInstance());
        fragments.add(ItemFragment.newInstance());
        fragments.add(ItemFragment.newInstance());
        fragments.add(ItemFragment.newInstance());
        fragments.add(ItemFragment.newInstance());

        itemModelMapper = new ItemModelMapper(this);
        itemEntityMapper = new ItemEntityMapper();
        itemCache = new ItemCacheImpl();
        itemDataStoreFactory = new ItemDataStoreFactory(this, itemCache);
        itemDataRepository = new ItemDataRepository(itemDataStoreFactory, itemEntityMapper);
        getItemList = new GetItemList(itemDataRepository);
        presenter = new ItemListPresenter(getItemList, itemModelMapper);

        presenter.addItemListViewForType(Item.Type.APPLICATION, fragments.get(0));
        presenter.addItemListViewForType(Item.Type.MUSIC, fragments.get(1));
        presenter.addItemListViewForType(Item.Type.FILE, fragments.get(2));
        presenter.addItemListViewForType(Item.Type.PICTURE, fragments.get(3));
        presenter.addItemListViewForType(Item.Type.VIDEO, fragments.get(4));

        presenter.initialize(Item.Type.APPLICATION);
        presenter.initialize(Item.Type.MUSIC);
        presenter.initialize(Item.Type.FILE);
        presenter.initialize(Item.Type.PICTURE);
        presenter.initialize(Item.Type.VIDEO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume(Item.Type.APPLICATION);
        presenter.resume(Item.Type.MUSIC);
        presenter.resume(Item.Type.FILE);
        presenter.resume(Item.Type.PICTURE);
        presenter.resume(Item.Type.VIDEO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause(Item.Type.APPLICATION);
        presenter.pause(Item.Type.MUSIC);
        presenter.pause(Item.Type.FILE);
        presenter.pause(Item.Type.PICTURE);
        presenter.pause(Item.Type.VIDEO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy(Item.Type.APPLICATION);
        presenter.destroy(Item.Type.MUSIC);
        presenter.destroy(Item.Type.FILE);
        presenter.destroy(Item.Type.PICTURE);
        presenter.destroy(Item.Type.VIDEO);
    }
}
