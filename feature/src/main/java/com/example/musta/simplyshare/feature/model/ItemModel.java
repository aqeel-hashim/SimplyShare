package com.example.musta.simplyshare.feature.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemModel implements Parcelable{
    private String name;
    private String path;
    private String size;
    private Drawable icon;

    public ItemModel(String name, String path, String size, Drawable icon) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.icon = icon;
    }

    public ItemModel() {
        this.name = "";
        this.path = "";
        this.size = "";
        this.icon = null;
    }

    protected ItemModel(Parcel in) {
        name = in.readString();
        path = in.readString();
        size = in.readString();
        Bitmap bitmap = in.readParcelable(getClass().getClassLoader());
        icon = new BitmapDrawable(bitmap);
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemModel itemModel = (ItemModel) o;

        return getSize().equals(itemModel.getSize()) && getName().equals(itemModel.getName()) && getPath().equals(itemModel.getPath()) && getIcon().equals(itemModel.getIcon());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPath().hashCode();
        result = 31 * result + getIcon().hashCode();
        result = 31 * result + getSize().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", icon=" + icon +
                '}';
    }

    @Override
    protected ItemModel clone() throws CloneNotSupportedException {
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(size);
        Bitmap bitmap = (Bitmap)((BitmapDrawable) icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
    }
}
