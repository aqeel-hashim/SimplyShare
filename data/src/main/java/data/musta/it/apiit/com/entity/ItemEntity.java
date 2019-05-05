package data.musta.it.apiit.com.entity;

import java.io.Serializable;
import java.util.Arrays;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemEntity implements Serializable{
    private String id;
    private String name;
    private String size;
    private String date;
    private String ext;
    private byte[] data;
    private String path;
    private Item.Type type;
    private boolean endTransfer = false;
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

    public boolean isEndTransfer() {
        return endTransfer;
    }

    public void setEndTransfer(boolean endTransfer) {
        this.endTransfer = endTransfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEntity)) return false;

        ItemEntity that = (ItemEntity) o;

        if (endTransfer != that.endTransfer) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (ext != null ? !ext.equals(that.ext) : that.ext != null) return false;
        if (!Arrays.equals(data, that.data)) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (ext != null ? ext.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(data);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (endTransfer ? 1 : 0);
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
                ", endTransfer=" + endTransfer +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
