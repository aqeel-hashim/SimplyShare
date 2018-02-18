package data.musta.it.apiit.com.repository.connection.transfer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import data.musta.it.apiit.com.R;
import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.interactor.ConnectionListner;
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
    private static String FolderName = "SimplyShare";
    private static final String TAG = FileServerAsyncTask.class.getSimpleName();
    private ConnectionListner connectionListner;
    private Context context;

    /**
     * @param context
     * @param port
     * @param listner
     * @param connectionListner
     */
    public FileServerAsyncTask(Context context, int port, TransferProgressListener listner, ConnectionListner connectionListner) {
        this.context = context;
        this.listner = listner;
        this.PORT = port;


        this.connectionListner = connectionListner;
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d(TAG, "doInBackground: " + "File Async task port" + "File Async task port-> " + PORT);
            // init handler for progressdialog
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(PORT));
            while (true) {


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
                        if (obj.getPublicKey() != null && obj.getPublicKey().length > 0) {
                            SharedPrefManager.getInstance(context).put("SIMPLYSHAREOPPOSINGPUBLICKEY", Base64.encodeToString(obj.getPublicKey(), Base64.DEFAULT));
                            connectionListner.connected();
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


                    copyRecievedFile(inputstream, new FileOutputStream(f),
                            ReceivedFileLength, obj.getItemEntity());

                    if (obj.getItemEntity().isEndTransfer()) {
                        //listner.transferFinished();

                        ois.close(); // close the ObjectOutputStream object after saving
                        // file to storage.
                        serverSocket.close();

                        break;
                    }


                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, "doInBackground: when trying to set obj type.");
                    e.printStackTrace();
                }
            }
            return "";
        } catch (IOException e) {
            Log.e(TAG, " When trying to open server receive: " + e.getMessage());
            return null;
        }
    }

    public boolean copyRecievedFile(InputStream inputStream,
                                    OutputStream out, Long length, ItemEntity file) {

        byte buf[] = new byte[FileTransferService.ByteSize];
        byte Decryptedbuf[] = new byte[FileTransferService.ByteSize];
        String Decrypted;
        int len;
        long total = 0;
        int progresspercentage = 0;
        int oldPercentage = -1;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                try {
                    String storedPublicKey = SharedPrefManager.getInstance(context).get("SIMPLYSHARECURRENTPRIVATEKEY", "EMPTY");

                    if (!Objects.equals(storedPublicKey, "EMPTY")) {
                        byte[] keyBytes = Base64.decode(storedPublicKey, Base64.DEFAULT);
                        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                        try {
                            KeyFactory kf = KeyFactory.getInstance("RSA");
                            PublicKey key = kf.generatePublic(spec);
                            Cipher cipher = Cipher.getInstance("RSA");
                            cipher.init(Cipher.DECRYPT_MODE, key);
                            buf = cipher.doFinal(buf);
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                            e.printStackTrace();
                        }
                    }
                    out.write(buf, 0, len);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    total += len;
                    if (length > 0) {
                        progresspercentage = (int) ((total * 100) / length);
                    }
                    if (oldPercentage != progresspercentage) {
                        //listner.updateProgress(progresspercentage, new ItemEntityMapper().transform(file));
                        Intent intentResponse = new Intent();
                        intentResponse.setAction(FileReceiverBroadcastReceiver.UPDATE_TRANSFER
                        );
                        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                        intentResponse.putExtra(FileReceiverBroadcastReceiver.PROGRESS, progresspercentage);
                        intentResponse.putExtra(FileReceiverBroadcastReceiver.PROGRESS_ITEM, file);
                        context.sendBroadcast(intentResponse);
                        oldPercentage = progresspercentage;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    //listner.endProgress(new ItemEntityMapper().transform(file));
                    Intent intentResponse = new Intent();
                    intentResponse.setAction(FileReceiverBroadcastReceiver.END_TRANSFER
                    );
                    intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                    intentResponse.putExtra(FileReceiverBroadcastReceiver.PROGRESS_ITEM, file);
                    context.sendBroadcast(intentResponse);
                }
            }
            // dismiss progress after sending
            //listner.endProgress(new ItemEntityMapper().transform(file));
            Intent intentResponse = new Intent();
            intentResponse.setAction(FileReceiverBroadcastReceiver.END_TRANSFER
            );
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra(FileReceiverBroadcastReceiver.PROGRESS_ITEM, file);
            context.sendBroadcast(intentResponse);
            out.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            return false;
        }
        return true;
    }
}
