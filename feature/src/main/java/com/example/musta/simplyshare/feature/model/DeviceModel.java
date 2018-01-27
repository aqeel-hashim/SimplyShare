package com.example.musta.simplyshare.feature.model;

/**
 * Created by musta on 11-Jan-18.
 */

public class DeviceModel {
    private String id;
    private String name;
    private int image;

    public DeviceModel(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
