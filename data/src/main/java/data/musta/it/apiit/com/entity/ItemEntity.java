package data.musta.it.apiit.com.entity;

import java.util.Arrays;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemEntity {
    private String id;
    private String name;
    private String size;
    private String date;
    private String ext;
    private byte[] data;
    private String path;
    private Item.Type type;

    public ItemEntity(String id, String name, String size,
                      String date, String ext, byte[] data,
                      String path, Item.Type type) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.date = date;
        this.ext = ext;
        this.data = data;
        this.path = path;
        this.type = type;
    }

    public ItemEntity() {
        this.name = "";
        this.size = "0";
        this.date = "";
        this.ext = "";
        this.data = new byte[100];
        this.path = "";
        this.type = Item.Type.APPLICATION;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getSize().equals(that.getSize())) return false;
        if (!getDate().equals(that.getDate())) return false;
        if (!getExt().equals(that.getExt())) return false;
        if (!Arrays.equals(getData(), that.getData())) return false;
        if (!getPath().equals(that.getPath())) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSize().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getExt().hashCode();
        result = 31 * result + Arrays.hashCode(getData());
        result = 31 * result + getPath().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", date='" + date + '\'' +
                ", ext='" + ext + '\'' +
                ", data=" + Arrays.toString(data) +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    protected ItemEntity clone() throws CloneNotSupportedException {
        return this;
    }
}
