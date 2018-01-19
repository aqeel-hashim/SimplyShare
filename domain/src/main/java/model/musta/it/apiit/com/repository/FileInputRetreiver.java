package model.musta.it.apiit.com.repository;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 20-Jan-18.
 */

public interface FileInputRetreiver {
    void dataRecieved(byte[] data, Item itemDescription);
}
