package model.musta.it.apiit.com.interactor;

import java.io.Serializable;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public interface TransferProgressListener extends Serializable {
    void updateProgress(int progress, Item file);

    void endProgress(Item file);

    void transferFinished();
}
