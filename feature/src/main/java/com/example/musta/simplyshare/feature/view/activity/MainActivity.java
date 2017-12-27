package com.example.musta.simplyshare.feature.view.activity;


import android.support.v7.app.ActionBar;
import android.view.View;

import com.example.musta.simplyshare.feature.R;

public class MainActivity extends BaseActivity {


    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initComponents() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void sendButtonClick(View view){

    }

    public void receiveButtonClick(View view){

    }
}
