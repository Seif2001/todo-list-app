package com._ml.todo_list_app.services;

import com._ml.todo_list_app.dtos.ItemDTO;
import com._ml.todo_list_app.models.Item;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ItemServiceIF {
    Page<Item> getAllItems(int page, int size);
    Optional<Item> getItemById(Long id);
    Item addItem(Item item);
    Item updateItem(Long id, ItemDTO updatedItem);
    void deleteItem(Long id);
}
