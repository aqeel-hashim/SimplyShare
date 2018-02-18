package data.musta.it.apiit.com.repository.connection.transfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.entity.mapper.ItemEntityMapper;
import model.musta.it.apiit.com.interactor.TransferProgressListener;

/**
 * Created by Aqeel Hashim on 03-Feb-18.
 */

public class FileReceiverBroadcastReceiver extends BroadcastReceiver {

    public static final String UPDATE_TRANSFER = "data.musta.it.apiit.com.repository.connection.transfer.UPDATE_RECEIVE_TRANSFER";
    public static final String END_TRANSFER = "data.musta.it.apiit.com.repository.connection.transfer.END_RECEIVE_TRANSFER";
    public static final String PROGRESS = "data.musta.it.apiit.com.repository.connection.transfer.PROGRESS_RECEIVE";
    public static final String PROGRESS_ITEM = "data.musta.it.apiit.com.repository.connection.transfer.PROGRESS_RECEIVE_ITEM";

    private TransferProgressListener listener;

    public FileReceiverBroadcastReceiver(TransferProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.equals(intent.getAction(), UPDATE_TRANSFER)) {
            listener.updateProgress(intent.getIntExtra(PROGRESS, 0), new ItemEntityMapper().transform((ItemEntity) intent.getSerializableExtra(PROGRESS_ITEM)));
        } else if (Objects.equals(intent.getAction(), END_TRANSFER)) {
            listener.endProgress(new ItemEntityMapper().transform((ItemEntity) intent.getSerializableExtra(PROGRESS_ITEM)));
        }
    }
}
