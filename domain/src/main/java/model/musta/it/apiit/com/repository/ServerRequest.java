package model.musta.it.apiit.com.repository;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 20-Jan-18.
 */

public interface ServerRequest {
    void addFileInputRetreiver(FileInputRetreiver fileInputRetreiver);
    void connect(String ip, int port);
    void send(byte[] data, Item itemDescription);
}
