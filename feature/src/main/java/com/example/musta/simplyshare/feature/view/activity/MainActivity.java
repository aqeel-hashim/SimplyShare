package com.example.musta.simplyshare.feature.view.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.musta.simplyshare.feature.R;

import data.musta.it.apiit.com.cache.ItemCacheImpl;
import data.musta.it.apiit.com.util.SharedPrefManager;

public class MainActivity extends BaseActivity {


    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.mainToolbar);
    }

    @Override
    public void initComponents() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void sendButtonClick(View view){
        startActivity(new Intent(this, SelectFilesActivity.class));
    }

    public void receiveButtonClick(View view){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPrefManager.getInstance(this.getSharedPreferences(ItemCacheImpl.cacheName, Context.MODE_PRIVATE)).clear();
        SharedPrefManager.getInstance(this.getSharedPreferences(ItemCacheImpl.cacheName, Context.MODE_PRIVATE)).put("EXPIRATION","expired");
    }
}
