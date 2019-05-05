package com.example.musta.simplyshare.feature.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.musta.simplyshare.feature.R;

/**
 * Created by musta on 05-Jan-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        setSupportActionBar(getToolbar());
        initComponents();
    }

    public abstract int getContentLayout();

    public abstract Toolbar getToolbar();

    public abstract void initComponents();

}
