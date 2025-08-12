package com._ml.todo_list_app.services;

import com._ml.todo_list_app.dtos.ItemDTO;
import com._ml.todo_list_app.exceptions.ItemCreationException;
import com._ml.todo_list_app.models.Item;
import com._ml.todo_list_app.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemService implements ItemServiceIF{
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }



    public Page<Item> getAllItems(int page, int size) {
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Item> getItemById(Long id){
        return itemRepository.findById(id);
    }

    public Item addItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            // Log error if you want
            throw new ItemCreationException("Failed to add item: " + e.getMessage(), e);
        }
    }

    public Item updateItem(Long id, ItemDTO updatedItem) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id " + id));

        existingItem.setName(updatedItem.getName());
        existingItem.setStatus(updatedItem.getStatus());

        return itemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchElementException("Item not found with id " + id);
        }
        itemRepository.deleteById(id);
    }
}
