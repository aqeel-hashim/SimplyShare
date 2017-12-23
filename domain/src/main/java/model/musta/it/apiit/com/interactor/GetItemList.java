package model.musta.it.apiit.com.interactor;

import java.util.List;

import model.musta.it.apiit.com.model.Item;
import model.musta.it.apiit.com.repository.ItemRepository;

/**
 * Created by musta on 23-Dec-17.
 */

public class GetItemList extends UseCase<List<Item>, Void> {

    private final ItemRepository itemRepository;

    public GetItemList(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    protected List<Item> buildUseCase(Void aVoid) {
        return itemRepository.items();
    }
}
