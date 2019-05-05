package com.example.musta.simplyshare.feature.presenter;

import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.model.Item;
import model.musta.it.apiit.com.repository.TransferManager;

/**
 * Created by Aqeel Hashim on 03-Feb-18.
 */

public class TransferViewPresenter {
    private TransferManager transferManager;

    public TransferViewPresenter(TransferManager transferManager) {
        this.transferManager = transferManager;
    }

    public void send(Item item, TransferProgressListener listner) {
        transferManager.send(item, listner);
    }

    public void unregisterReceivers() {
        transferManager.unregisterReceivers();
    }
}
