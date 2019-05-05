package data.musta.it.apiit.com.repository.connection.transfer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import data.musta.it.apiit.com.entity.DeviceEntity;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.repository.connection.DeviceWifiPP2PManager;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.model.WifiP2pInfo;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

/*
     * Async class that has to be called when connection establish first time. Its main motive is to send blank message
     * to server so that server knows the IP address of client to send files Bi-Directional.
     */
public class FirstPublicKeyMessage extends AsyncTask<String, Void, String> {

    private final WifiP2pInfo info;

    private Context activity;

    private static final String TAG = FirstPublicKeyMessage.class.getSimpleName();
    private DeviceEntity currentDevice;

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public FirstPublicKeyMessage(WifiP2pInfo info, Context activity, DeviceEntity currentDevice) {
        // TODO Auto-generated constructor stub
        this.info = info;

        this.activity = activity;
        this.currentDevice = currentDevice;

        try {
            this.keyGen = KeyPairGenerator.getInstance("RSA");
            this.keyGen.initialize(1024);
            this.pair = this.keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

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

            TransferEntity entity = new TransferEntity(currentDevice);
            entity.setPublicKey(this.publicKey.getEncoded());
            SharedPrefManager.getInstance(activity).put("SIMPLYSHARECURRENTPRIVATEKEY", Base64.encodeToString(this.privateKey.getEncoded(), Base64.DEFAULT));
            serviceIntent.putExtra(FileTransferService.inetaddress,
                    entity);

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
