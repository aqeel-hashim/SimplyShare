package com.example.musta.simplyshare.feature.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.musta.simplyshare.feature.R;
import com.example.musta.simplyshare.feature.view.fragment.ReceiveFragment;

import data.musta.it.apiit.com.cache.ItemCacheImpl;
import data.musta.it.apiit.com.util.SharedPrefManager;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white, null));
        return toolbar;
    }

    @Override
    public void initComponents() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void sendButtonClick(View view){
        startActivity(new Intent(this, SelectFilesActivity.class));
    }

    public void receiveButtonClick(View view){
        findViewById(R.id.container).setVisibility(View.VISIBLE);
        ReceiveFragment fragment = ReceiveFragment.newInstance("","");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.container).setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPrefManager.getInstance(this.getSharedPreferences(ItemCacheImpl.cacheName, Context.MODE_PRIVATE)).clear();
        SharedPrefManager.getInstance(this.getSharedPreferences(ItemCacheImpl.cacheName, Context.MODE_PRIVATE)).put("EXPIRATION","expired");
    }
}
