package data.musta.it.apiit.com.repository.datasource;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import data.musta.it.apiit.com.cache.ItemCache;
import data.musta.it.apiit.com.entity.ItemEntity;
import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemCursorDataStore implements ItemDataSource {

    private Context context;
    private ItemCache itemCache;

    public ItemCursorDataStore(Context context, ItemCache itemCache) {
        this.context = context;
        this.itemCache = itemCache;
    }

    @Override
    public List<ItemEntity> items(Item.Type provider) {
        switch (provider){
            case APPLICATION:
                final PackageManager pm = context.getPackageManager();
                List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                ArrayList<ItemEntity> applicationList = new ArrayList<ItemEntity>();
                for (ApplicationInfo packageInfo : packages) {
                    System.out.println("Installed package :" + packageInfo.loadLabel(context.getPackageManager()).toString());
                    System.out.println("Source dir : " + packageInfo.publicSourceDir);
                    System.out.println("Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                    File file = new File(packageInfo.publicSourceDir);
                    float size = file.length();
                    String ext = packageInfo.publicSourceDir;
                    System.out.println(ext.substring(ext.lastIndexOf(".") + 1).trim());
                    ext = ext.substring(ext.lastIndexOf(".") + 1).trim();
                    int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
                    if((packageInfo.flags & mask) == 0) {
                        ItemEntity appModel = null;
                        try {
                            File f = new File(packageInfo.dataDir);
                            FileInputStream fileInputStream = new FileInputStream(f);
                            appModel = new ItemEntity(packageInfo.packageName, packageInfo.loadLabel(context.getPackageManager()).toString(),
                                    String.valueOf(size), new Date(context
                                    .getPackageManager()
                                    .getPackageInfo(packageInfo.packageName, 0)
                                    .firstInstallTime).toString(), ext,
                                    readFile(fileInputStream, (int) f.length()),
                                    packageInfo.dataDir, Item.Type.APPLICATION);
                        } catch (PackageManager.NameNotFoundException | FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        applicationList.add(appModel);
                    }
                }
                applicationList.sort(Comparator.comparing(ItemEntity::getName));
                itemCache.put(Item.Type.APPLICATION, applicationList);
                return applicationList;
            case FILE:
                ContentResolver cr = context.getContentResolver();
                Uri uri = MediaStore.Files.getContentUri("external");
                String[] projection = null;
                String sortOrder = null; // unordered
                String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
                String mimeTypePDF = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
                String[] selectionArgs = new String[]{mimeTypePDF};
                Cursor allNonMediaFiles = cr.query(uri, projection, selection, selectionArgs, sortOrder);
                List<ItemEntity> itemEntities = readCursor(allNonMediaFiles, null, Item.Type.FILE);
                itemCache.put(Item.Type.APPLICATION, itemEntities);
                return itemEntities;
            case MUSIC:

                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                if (cursor == null) {
                    return null;
                }
                MediaMetadataRetriever mmr;
                List<ItemEntity> itemEntitiesMusic = readCursor(cursor, MediaStore.Audio.Media.IS_MUSIC, Item.Type.MUSIC);
                itemCache.put(Item.Type.APPLICATION, itemEntitiesMusic);
                return itemEntitiesMusic;
            case VIDEO:
                Cursor cursorVideo = context.getContentResolver().query(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Video.Media.DEFAULT_SORT_ORDER);
                List<ItemEntity> itemEntitiesVideo = readCursor(cursorVideo, null, Item.Type.VIDEO);
                itemCache.put(Item.Type.APPLICATION, itemEntitiesVideo);
                return itemEntitiesVideo;
            case PICTURE:
                Cursor cursorPicture = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                List<ItemEntity> itemEntitiesPicture = readCursor(cursorPicture, null, Item.Type.PICTURE);
                itemCache.put(Item.Type.APPLICATION, itemEntitiesPicture);
                return itemEntitiesPicture;
        }
        return null;
    }

    public byte[] readFile(FileInputStream fileInputStream, int fileLength) {
        byte fileContent[] = new byte[fileLength];
        try {
            fileInputStream.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public List<ItemEntity> readCursor(Cursor cursor, String check, Item.Type type){
        List<ItemEntity> items = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int isMusic = 1;
            if(check != null && !check.isEmpty()) {
                isMusic = cursor.getInt(cursor
                        .getColumnIndex(check));
            }

            if (isMusic != 0) {
                if (!new File(cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA))).exists()) {
                    continue;
                }
                String ext = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA));
                ext = ext.substring(ext.lastIndexOf(".") + 1).trim();
                File f = new File(cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA)));
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(f);
                    byte fileContent[] = readFile(fileInputStream, (int) f.length());
                    ItemEntity itemEntity = new ItemEntity(String.valueOf(cursor.getLong(cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media._ID))),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)),
                            ext,
                            fileContent,
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DATA)), type);
                    items.add(itemEntity);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return items;
    }

}
