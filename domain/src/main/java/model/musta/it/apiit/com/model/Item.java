package model.musta.it.apiit.com.model;

import java.io.Serializable;
import java.util.Arrays;

public class Item implements Serializable {
    public enum Type implements Serializable {
        APPLICATION,
        FILE,
        MUSIC,
        PICTURE,
        VIDEO
    }
    private String id;
    private String name;
    private double size;
    private byte[] data;
    private String path;
    private Type type;

    public Item(String id, String name, double size, byte[] data, String path, Type type) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.data = data;
        this.path = path;
        this.type = type;
    }

    public Item() {
        this.id = "";
        this.name = "";
        this.size = 0.0;
        this.data = new byte[100];
        this.path = "";
        this.type = Type.APPLICATION;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.getSize(), getSize()) != 0) return false;
        if (!getId().equals(item.getId())) return false;
        if (!getName().equals(item.getName())) return false;
        if (!Arrays.equals(getData(), item.getData())) return false;
        if (!getPath().equals(item.getPath())) return false;
        return getType() == item.getType();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        temp = Double.doubleToLongBits(getSize());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(getData());
        result = 31 * result + getPath().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", data=" + Arrays.toString(data) +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    protected Item clone() throws CloneNotSupportedException {
        return this;
    }
}
