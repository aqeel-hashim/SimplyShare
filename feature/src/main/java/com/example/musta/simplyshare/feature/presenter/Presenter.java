package com.example.musta.simplyshare.feature.presenter;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */
public interface Presenter {
    void resume(Item.Type provider);
    void pause(Item.Type provider);
    void destroy(Item.Type provider);
}
