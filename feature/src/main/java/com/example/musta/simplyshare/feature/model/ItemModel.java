package com.example.musta.simplyshare.feature.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemModel {
    private String name;
    private String path;
    private Drawable icon;

    public ItemModel(String name, String path, Drawable icon) {
        this.name = name;
        this.path = path;
        this.icon = icon;
    }

    public ItemModel() {
        this.name = "";
        this.path = "";
        this.icon = null;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemModel itemModel = (ItemModel) o;

        if (!getName().equals(itemModel.getName())) return false;
        if (!getPath().equals(itemModel.getPath())) return false;
        return getIcon().equals(itemModel.getIcon());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPath().hashCode();
        result = 31 * result + getIcon().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", icon=" + icon +
                '}';
    }

    @Override
    protected ItemModel clone() throws CloneNotSupportedException {
        return this;
    }
}
