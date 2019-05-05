// Copyright 2011 Google Inc. All Rights Reserved.

package data.musta.it.apiit.com.repository.connection.transfer;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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

import data.musta.it.apiit.com.BuildConfig;
import data.musta.it.apiit.com.entity.ItemEntity;
import data.musta.it.apiit.com.entity.TransferEntity;
import data.musta.it.apiit.com.util.SharedPrefManager;
import model.musta.it.apiit.com.interactor.TransferProgressListener;

import static data.musta.it.apiit.com.repository.connection.transfer.FileTransferBroadcastReceiver.PROGRESS_ITEM;

/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    public static int ActualFilelength;
    private static int Percentage;
    Handler mHandler;

    public static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "data.musta.it.apiit.com.SimplyShare.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_ITEM_ENTITY = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public static final String EXTRAS_FILE_TRANSFER_LISTNER = "FILETRANSFERLISTNER";

    public static int PORT = 8888;
    public static final String inetaddress = "inetaddress";
    public static final int ByteSize = 512;
    public static final String Extension = "extension";
    public static final String Filelength = "filelength";
    private static final String TAG = FileTransferService.class.getSimpleName();

    public static TransferProgressListener listner;

    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mHandler = new Handler();
    }

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
            int actualFileLength = intent.getExtras().getInt(Filelength);
            TransferEntity item = (TransferEntity) intent.getExtras().getSerializable(EXTRAS_ITEM_ENTITY);

            try {
                Log.d(TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(TAG, "Client socket - " + socket.isConnected());
                OutputStream stream = socket.getOutputStream();
                ContentResolver cr = context.getContentResolver();
                InputStream is = null;

                /**
                 * Object that is used to send file name with extension and recieved on other side.
                 * */


                ObjectOutputStream oos = new ObjectOutputStream(stream);

                oos.writeObject(item);

                try {
                    is = cr.openInputStream(Uri.fromFile(new File(item.getItemEntity().getPath())));
                } catch (FileNotFoundException e) {
                    Log.d(TAG, " when trying get input stream for file: " + e.toString());
                }
                copyFile(actualFileLength, is, stream, item.getItemEntity());
                Log.d(TAG, "Client: Data written");
                oos.close();    //close the ObjectOutputStream after sending data.
            } catch (IOException e) {
                Log.e(TAG, " when trying to connect: " + e.getMessage());
                e.printStackTrace();
                Log.e(TAG, "onHandleIntent: " + "Unable to connect host" + "service socket error in wififiletransferservice class");
                mHandler.post(() -> {
                    // TODO Auto-generated method stub
                    Toast.makeText(FileTransferService.this, "Paired Device is not Ready to receive the file", Toast.LENGTH_LONG).show();
                });
//                listner.endProgress();
                Intent intentResponse = new Intent();
                intentResponse.setAction(FileTransferBroadcastReceiver.END_TRANSFER);
                intent.putExtra(PROGRESS_ITEM, item.getItemEntity());
                intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                sendBroadcast(intentResponse);

            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            Log.e(TAG, "onHandleIntent: when closing");
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }


    public static void openFile(String stringUrl, Context context) {
        Uri uri = Uri.parse(stringUrl);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri fileURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    new File(stringUrl));
            uri = fileURI;
        } else {
            uri = Uri.parse("file://" + uri.getPath());
        }
        if (stringUrl.toString().contains(".doc") || stringUrl.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (stringUrl.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (stringUrl.toString().contains(".ppt") || stringUrl.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (stringUrl.toString().contains(".xls") || stringUrl.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (stringUrl.toString().contains(".zip") || stringUrl.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (stringUrl.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (stringUrl.toString().contains(".wav") || stringUrl.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (stringUrl.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (stringUrl.toString().contains(".jpg") || stringUrl.toString().contains(".jpeg") || stringUrl.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/*");
        } else if (stringUrl.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (stringUrl.toString().contains(".3gp") || stringUrl.toString().contains(".mpg") || stringUrl.toString().contains(".mpeg") || stringUrl.toString().contains(".mpe") || stringUrl.toString().contains(".mp4") || stringUrl.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    private boolean copyFile(int ActualFilelength, InputStream inputStream, OutputStream out, ItemEntity itemEntity) {
        long total = 0;
        int Percentage = 0;
        int oldPercentage = -1;
        byte buf[] = new byte[FileTransferService.ByteSize];
        if (buf == null) return false;

        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {

                String storedPublicKey = SharedPrefManager.getInstance(getApplicationContext()).get("SIMPLYSHAREOPPOSINGPUBLICKEY", "EMPTY");

                if (!Objects.equals(storedPublicKey, "EMPTY")) {
                    byte[] keyBytes = Base64.decode(storedPublicKey, Base64.DEFAULT);
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                    try {
                        KeyFactory kf = KeyFactory.getInstance("RSA");
                        PublicKey key = kf.generatePublic(spec);
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, key);
                        buf = cipher.doFinal(buf);
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                }
                out.write(buf, 0, len);
                try {
                    total += len;
                    if (ActualFilelength > 0) {
                        Percentage = (int) ((total * 100) / ActualFilelength);
                    }
                    if (oldPercentage != Percentage) {
                        Intent intentResponse = new Intent();
                        intentResponse.setAction(FileTransferBroadcastReceiver.UPDATE_TRANSFER
                        );
                        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                        intentResponse.putExtra(FileTransferBroadcastReceiver.PROGRESS, Percentage);
                        intentResponse.putExtra(PROGRESS_ITEM, itemEntity);
                        sendBroadcast(intentResponse);
                        oldPercentage = Percentage;
                    }
                    //listner.updateProgress(Percentage);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Percentage = 0;
                    ActualFilelength = 0;

                    Intent intentResponse = new Intent();
                    intentResponse.setAction(FileTransferBroadcastReceiver.END_TRANSFER);
                    intentResponse.putExtra(PROGRESS_ITEM, itemEntity);
                    intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intentResponse);
//                    listner.endProgress();
                }
            }

            //listner.endProgress();
            Intent intentResponse = new Intent();
            intentResponse.setAction(FileTransferBroadcastReceiver.END_TRANSFER);
            intentResponse.putExtra(PROGRESS_ITEM, itemEntity);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(intentResponse);
            out.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            return false;
        }
        return true;
    }


}
