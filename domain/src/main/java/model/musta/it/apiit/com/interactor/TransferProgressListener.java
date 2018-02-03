package model.musta.it.apiit.com.interactor;

import java.io.Serializable;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public interface TransferProgressListener extends Serializable {
    void updateProgress(int progress);

    void endProgress();

    void transferFinished();
}
