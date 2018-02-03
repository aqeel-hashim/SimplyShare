package data.musta.it.apiit.com.repository.connection.transfer;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import data.musta.it.apiit.com.entity.TransferEntity;

public class WiFiClientIPTransferService extends IntentService {

    private static final String TAG = WiFiClientIPTransferService.class.getSimpleName();

    public WiFiClientIPTransferService(String name) {
        super(name);
    }

    public WiFiClientIPTransferService() {
        super("WiFiClientIPTransferService");
    }


    Handler mHandler;

    /*
         * (non-Javadoc)
         * @see android.app.IntentService#onHandleIntent(android.content.Intent)
         */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(FileTransferService.ACTION_SEND_FILE)) {
            String host = intent.getExtras().getString(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS);
            TransferEntity InetAddress = (TransferEntity) intent.getExtras().getSerializable(FileTransferService.inetaddress);
            Log.e(TAG, "onHandleIntent: " + "LocalIp Received while first connect" + "host address" + host);

            Socket socket = new Socket();
            int port = intent.getExtras().getInt(FileTransferService.EXTRAS_GROUP_OWNER_PORT);

            try {

                Log.d(TAG, "Opening client socket for First tiime- ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), FileTransferService.SOCKET_TIMEOUT);
                Log.d(TAG, "Client socket - " + socket.isConnected());
                OutputStream stream = socket.getOutputStream();
                
               /*
                * Object that is used to send file name with extension and recieved on other side.
                */
                ObjectOutputStream oos = new ObjectOutputStream(stream);

                oos.writeObject(InetAddress);
                System.out.println("Sending request to Socket Server");

                oos.close();    //close the ObjectOutputStream after sending data.
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            Log.e(TAG, "onHandleIntent: " + "WiFiClientIP Service" + "First Connection service socket closed");
                            socket.close();
                        } catch (Exception e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

}
