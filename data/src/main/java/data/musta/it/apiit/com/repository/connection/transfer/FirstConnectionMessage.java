package data.musta.it.apiit.com.repository.connection.transfer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import data.musta.it.apiit.com.entity.DeviceEntity;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import model.musta.it.apiit.com.model.WifiP2pInfo;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

/*
     * Async class that has to be called when connection establish first time. Its main motive is to send blank message
     * to server so that server knows the IP address of client to send files Bi-Directional.
     */
public class FirstConnectionMessage extends AsyncTask<String, Void, String> {

    private final WifiP2pInfo info;

    private Context activity;

    private static final String TAG = FirstConnectionMessage.class.getSimpleName();
    private DeviceEntity currentDevice;

    public FirstConnectionMessage(WifiP2pInfo info, Context activity, DeviceEntity currentDevice) {
        // TODO Auto-generated constructor stub
        this.info = info;

        this.activity = activity;
        this.currentDevice = currentDevice;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        Log.e(TAG, "doInBackground: " + "On first Connect" + "On first Connect");

        Intent serviceIntent = new Intent(activity,
                WiFiClientIPTransferService.class);

        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);

        if (info.groupOwnerAddress.getHostAddress() != null) {
            serviceIntent.putExtra(
                    FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                    info.groupOwnerAddress.getHostAddress());

            serviceIntent.putExtra(
                    FileTransferService.EXTRAS_GROUP_OWNER_PORT,
                    FileTransferService.PORT);
            serviceIntent.putExtra(FileTransferService.inetaddress,
                    new TransferEntity(currentDevice));

        }

        activity.startService(serviceIntent);

        return "success";
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null) {
            if (result.equalsIgnoreCase("success")) {

                Log.d(TAG, "onPostExecute: " + "On first Connect" +
                        "On first Connect sent to asynctask");
                DeviceWifiPP2PManager.ClientCheck = true;
            }
        }

    }

}
