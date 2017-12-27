package com.example.musta.simplyshare.feature.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by musta on 27-Dec-17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        initComponents();
    }

    public abstract int getContentLayout();

    public abstract void initComponents();

}
