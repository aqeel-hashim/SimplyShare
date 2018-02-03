package data.musta.it.apiit.com.repository.connection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.Toast;

import data.musta.it.apiit.com.R;
import data.musta.it.apiit.com.entity.DeviceEntity;
import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.entity.WifiP2pDevice;
import data.musta.it.apiit.com.entity.mapper.ItemEntityMapper;
import data.musta.it.apiit.com.repository.connection.transfer.FileTransferBroadcastReceiver;
import data.musta.it.apiit.com.repository.connection.transfer.FileTransferService;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.interactor.TransferProgressListener;
import model.musta.it.apiit.com.model.Item;
import model.musta.it.apiit.com.repository.TransferManager;

import static data.musta.it.apiit.com.cache.ItemCacheImpl.cacheName;

/**
 * Created by Aqeel Hashim on 28-Jan-18.
 */

public class WifiP2pTransferManager implements TransferManager {


    private Context context;
    private FileTransferBroadcastReceiver broadcastReceiver;

    public WifiP2pTransferManager(Context context) {
        this.context = context;
    }

    @Override
    public void send(Item item, TransferProgressListener listener) {
        ItemEntity itemEntity = new ItemEntityMapper().revert(item);
        int ActualFilelength = (int) Double.parseDouble(itemEntity.getSize());

        Intent serviceIntent = new Intent(context, FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);


        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, itemEntity.getPath());
        serviceIntent.putExtra(FileTransferService.EXTRAS_ITEM_ENTITY, new TransferEntity(itemEntity, new DeviceEntity("", new WifiP2pDevice("", ""))));

        String Ip = SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)).get(context.getString(R.string.pref_WiFiClientIp), "");
        String OwnerIp = SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)).get(context.getString(R.string.pref_GroupOwnerAddress), "");


        if (!TextUtils.isEmpty(OwnerIp) && OwnerIp.length() > 0) {
            String host = null;
            int sub_port = -1;

            String ServerBool = SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)).get(context.getString(R.string.pref_ServerBoolean), "");
            if (!TextUtils.isEmpty(ServerBool) && ServerBool.equalsIgnoreCase("true") && !TextUtils.isEmpty(Ip)) {
                host = Ip;
                sub_port = FileTransferService.PORT;


                serviceIntent
                        .putExtra(
                                FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                                Ip);

            } else {
                FileTransferService.PORT = 8888;
                host = OwnerIp;
                sub_port = FileTransferService.PORT;


                serviceIntent.putExtra(
                        FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                        OwnerIp);
            }


            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, FileTransferService.PORT);

            serviceIntent.putExtra(FileTransferService.Extension, itemEntity.getExt());

            serviceIntent.putExtra(FileTransferService.Filelength, ActualFilelength);


            if (host != null && sub_port != -1) {
                Toast.makeText(context, "Connecting to host", Toast.LENGTH_SHORT).show();
                //context.startService(serviceIntent);
                context.startService(serviceIntent);

                broadcastReceiver = new FileTransferBroadcastReceiver(listener);
                IntentFilter intentFilter = new IntentFilter(FileTransferBroadcastReceiver.UPDATE_TRANSFER);
                intentFilter.addAction(FileTransferBroadcastReceiver.END_TRANSFER);
                intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
                context.registerReceiver(broadcastReceiver, intentFilter);

            } else {
                Toast.makeText(context, "Host Address not found, Please Re-Connect", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Host Address not found, Please Re-Connect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void unregisterReceivers() {
        context.unregisterReceiver(broadcastReceiver);
    }
}
