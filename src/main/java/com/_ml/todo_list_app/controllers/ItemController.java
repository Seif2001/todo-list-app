package com._ml.todo_list_app.controllers;

import com._ml.todo_list_app.errors.ApiError;
import com._ml.todo_list_app.exceptions.ItemCreationException;
import com._ml.todo_list_app.models.Item;
import com._ml.todo_list_app.services.ItemService;
import com._ml.todo_list_app.services.ItemServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemServiceIF itemService;

    @Autowired
    public ItemController(ItemServiceIF itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<?> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Item> items = itemService.getAllItems(page, size);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            ApiError error = new ApiError(500, "Failed to fetch items: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) { // ? because it is based on whether we return an error or not
        try {
            Item savedItem = itemService.addItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (ItemCreationException e) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Error creating item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<Item> optionalItem = itemService.getItemById(id);

        if (optionalItem.isPresent()) {
            return ResponseEntity.ok(optionalItem.get());
        } else {
            ApiError error = new ApiError(404, "Item not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        try {
            Item updated = itemService.updateItem(id, updatedItem);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            ApiError error = new ApiError(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiError error = new ApiError(400, "Error updating item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            ApiError error = new ApiError(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ApiError error = new ApiError(400, "Error deleting item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }


}
