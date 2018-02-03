package data.musta.it.apiit.com.repository.connection.transfer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import data.musta.it.apiit.com.R;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.interactor.TransferProgressListener;

import static data.musta.it.apiit.com.cache.ItemCacheImpl.cacheName;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class FileServerAsyncTask extends AsyncTask<String, String, String> {

    private long ReceivedFileLength;
    private int PORT;
    private TransferProgressListener listner;
    private String WiFiClientIp;
    public static String FolderName = "SimplyShare";
    private static final String TAG = FileServerAsyncTask.class.getSimpleName();

    private Context context;

    /**
     * @param context
     * @param port
     * @param listner
     */
    public FileServerAsyncTask(Context context, int port, TransferProgressListener listner) {
        this.context = context;
        this.listner = listner;
        this.PORT = port;


    }


    @Override
    protected String doInBackground(String... params) {
        try {


            Log.d(TAG, "doInBackground: " + "File Async task port" + "File Async task port-> " + PORT);
            // init handler for progressdialog
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(PORT));

            Log.d(TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();


            Log.d(TAG, "doInBackground: " + "Client's InetAddresssss  " + "" + client.getInetAddress());

            WiFiClientIp = client.getInetAddress().getHostAddress();
            ObjectInputStream ois = new ObjectInputStream(
                    client.getInputStream());
            TransferEntity obj = null;
            String InetAddress;
            try {
                obj = (TransferEntity) ois.readObject();

                if (obj != null) {
                    InetAddress = obj.getDeviceEntity().getIpAddress();
                    if (InetAddress != null && !InetAddress.isEmpty()) {

                        Log.e(TAG, "doInBackground: " + "File Async Group Client Ip" + "port-> "
                                + WiFiClientIp);

                        SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE))
                                .put(context.getString(R.string.pref_WiFiClientIp),
                                        WiFiClientIp);


                        Log.e(TAG, "doInBackground: " + "File Async Group Client Ip from SHAREDPrefrence" +
                                "port-> "
                                + SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE))
                                .get(context.getString(R.string.pref_WiFiClientIp), "EMPTY"));
                        //set boolean true which identifiy that this device will act as server.


                        SharedPrefManager.getInstance(context.getSharedPreferences(cacheName, Context.MODE_PRIVATE))
                                .put(context.getString(R.string.pref_ServerBoolean),
                                        "true");
                        ois.close(); // close the ObjectOutputStream object
                        // after saving
                        serverSocket.close();

                        return "Demo";
                    }

//                    final Runnable r = () -> {
//                        // TODO Auto-generated method stub
//                        listner.endProgress();
//                    };
                    //handler.post(r);
                    if (obj.getItemEntity() != null && obj.getItemEntity().getName() != null && !obj.getItemEntity().getName().isEmpty())
                    Log.d(TAG, "doInBackground: " + "FileName got from socket on other side->>> " +
                            obj.getItemEntity().getName());
                }

                final File f = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + FolderName + "/"
                                + obj.getItemEntity().getName() + "." + obj.getItemEntity().getExt());

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();

				/*
                 * Recieve file length and copy after it
				 */
                this.ReceivedFileLength = (long) Double.parseDouble(obj.getItemEntity().getSize());

                InputStream inputstream = client.getInputStream();


                FileTransferService.copyRecievedFile(inputstream, new FileOutputStream(f),
                        ReceivedFileLength, listner);

                if (obj.getItemEntity().isEndTransfer())
                    listner.transferFinished();

                ois.close(); // close the ObjectOutputStream object after saving
                // file to storage.
                serverSocket.close();

				/*
                 * Set file related data and decrypt file in postExecute.
				 */
                return f.getAbsolutePath();

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
