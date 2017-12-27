package com.example.musta.simplyshare.feature.view;

import android.content.Context;

/**
 * Created by musta on 27-Dec-17.
 */

public interface LoadDataView {
    void showLoading();
    void hideLoading();
    void showError(String errorMessage);
    Context context();
}
