package com.example.musta.simplyshare.feature.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemModel implements Parcelable{
    private String name;
    private String path;
    private String size;
    private Drawable icon;
    private Item.Type type;

    public ItemModel(String name, String path, String size, Drawable icon, Item.Type type) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.icon = icon;
        this.type = type;
    }

    public ItemModel() {
        this.name = "";
        this.path = "";
        this.size = "";
        this.icon = null;
        this.type = Item.Type.APPLICATION;
    }

    protected ItemModel(Parcel in) {
        name = in.readString();
        path = in.readString();
        size = in.readString();
        Bitmap bitmap = in.readParcelable(getClass().getClassLoader());
        icon = new BitmapDrawable(bitmap);
        this.type = Item.Type.values()[in.readInt()];
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

    public Item.Type getType() {
        return type;
    }

    public void setType(Item.Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemModel)) return false;

        ItemModel itemModel = (ItemModel) o;

        if (name != null ? !name.equals(itemModel.name) : itemModel.name != null) return false;
        if (path != null ? !path.equals(itemModel.path) : itemModel.path != null) return false;
        if (size != null ? !size.equals(itemModel.size) : itemModel.size != null) return false;
        if (!icon.equals(itemModel.icon)) return false;
        return type == itemModel.type;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + icon.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", icon=" + icon +
                ", type=" + type +
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
        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
        dest.writeInt(type.ordinal());
    }
}
