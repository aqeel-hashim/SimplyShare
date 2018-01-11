package data.musta.it.apiit.com.repository.datasource.item;

import java.util.List;

import data.musta.it.apiit.com.entity.ItemEntity;
import model.musta.it.apiit.com.model.Item.Type;
/**
 * Created by musta on 23-Dec-17.
 */

public interface ItemDataSource {

    List<ItemEntity> items(Type provider);
}
