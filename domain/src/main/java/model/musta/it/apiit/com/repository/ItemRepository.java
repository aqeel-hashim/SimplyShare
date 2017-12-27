package model.musta.it.apiit.com.repository;

import java.util.List;

import model.musta.it.apiit.com.model.Item;

/**
 * Created by musta on 23-Dec-17.
 */

public interface ItemRepository {
    List<Item> items(Item.Type provider);
}
