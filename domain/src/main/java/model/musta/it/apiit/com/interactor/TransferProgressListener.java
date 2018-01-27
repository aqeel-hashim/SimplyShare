package model.musta.it.apiit.com.interactor;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public interface TransferProgressListener {
    void updateProgress(int progress);

    void endProgress();

    void transferFinished();
}
