package model.musta.it.apiit.com.repository;

import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public interface TransferManager {
    void send(Item item, TransferProgressListener listener);
}
