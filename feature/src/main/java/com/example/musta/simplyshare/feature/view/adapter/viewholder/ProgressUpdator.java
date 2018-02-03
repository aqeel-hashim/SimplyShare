package com.example.musta.simplyshare.feature.view.adapter.viewholder;

import android.widget.ProgressBar;

import model.musta.it.apiit.com.interactor.TransferProgressListener;

/**
 * Created by Aqeel Hashim on 03-Feb-18.
 */

public class ProgressUpdator implements TransferProgressListener {

    private ProgressBar progressBar;


    public ProgressUpdator(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void updateProgress(int progress) {
        progressBar.setProgress(progress, true);
    }

    @Override
    public void endProgress() {
        progressBar.setProgress(100, true);
    }

    @Override
    public void transferFinished() {

    }
}
