package com.example.musta.simplyshare.feature.view.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.model.ItemModel;
import com.example.musta.simplyshare.feature.model.mapper.ItemModelMapper;
import com.example.musta.simplyshare.feature.presenter.ItemListPresenter;
import com.example.musta.simplyshare.feature.view.ItemListView;
import com.example.musta.simplyshare.feature.view.adapter.SectionPagerAdapter;
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
        TabLayout tabLayout = findViewById(R.id.my_tab_layout);
        final ViewPager viewPager = findViewById(R.id.my_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Apps"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Files"));
        tabLayout.addTab(tabLayout.newTab().setText("Pictures"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));

        List<ItemFragment> fragments = new Vector<>();
        fragments.add(ItemFragment.newInstance(Item.Type.APPLICATION));
        fragments.add(ItemFragment.newInstance(Item.Type.MUSIC));
        fragments.add(ItemFragment.newInstance(Item.Type.FILE));
        fragments.add(ItemFragment.newInstance(Item.Type.PICTURE));
        fragments.add(ItemFragment.newInstance(Item.Type.VIDEO));

        itemModelMapper = new ItemModelMapper(this);
        itemEntityMapper = new ItemEntityMapper();
        itemCache = new ItemCacheImpl(this);
        itemDataStoreFactory = new ItemDataStoreFactory(this, itemCache);
        itemDataRepository = new ItemDataRepository(itemDataStoreFactory, itemEntityMapper);
        getItemList = new GetItemList(itemDataRepository);
        presenter = new ItemListPresenter(getItemList, itemModelMapper);

        presenter.addItemListViewForType(Item.Type.APPLICATION, fragments.get(0));
        presenter.addItemListViewForType(Item.Type.MUSIC, fragments.get(1));
        presenter.addItemListViewForType(Item.Type.FILE, fragments.get(2));
        presenter.addItemListViewForType(Item.Type.PICTURE, fragments.get(3));
        presenter.addItemListViewForType(Item.Type.VIDEO, fragments.get(4));


        fragments.get(0).setPresenter(presenter);
        fragments.get(1).setPresenter(presenter);
        fragments.get(2).setPresenter(presenter);
        fragments.get(3).setPresenter(presenter);
        fragments.get(4).setPresenter(presenter);

        final SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
