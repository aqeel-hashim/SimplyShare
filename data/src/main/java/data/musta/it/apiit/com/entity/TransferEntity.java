package data.musta.it.apiit.com.entity;

import java.io.Serializable;

/**
 * Created by Aqeel Hashim on 27-Jan-18.
 */

public class TransferEntity implements Serializable {

    private ItemEntity itemEntity;
    private DeviceEntity deviceEntity;
    private byte[] PublicKey;

    public TransferEntity(DeviceEntity ip) {
        this.deviceEntity = ip;
    }

    public TransferEntity(ItemEntity itemEntity, DeviceEntity deviceEntity) {
        this.itemEntity = itemEntity;
        this.deviceEntity = deviceEntity;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    public DeviceEntity getDeviceEntity() {
        return deviceEntity;
    }

    public void setDeviceEntity(DeviceEntity deviceEntity) {
        this.deviceEntity = deviceEntity;
    }

    public byte[] getPublicKey() {
        return PublicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        PublicKey = publicKey;
    }
}
