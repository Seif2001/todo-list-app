package com._ml.todo_list_app.controllers;

import com._ml.todo_list_app.dtos.ItemDTO;
import com._ml.todo_list_app.errors.ApiError;
import com._ml.todo_list_app.exceptions.ItemCreationException;
import com._ml.todo_list_app.models.Item;
import com._ml.todo_list_app.services.ItemServiceIF;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


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



    @Operation(summary = "Get all items", description = "Retrieve a paginated list of items")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of items returned",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Page<Item> example",
                                    value = """
{
    "content": [
        {"name":"todo1","status":false,"itemId":2},
        {"name":"todo1","status":false,"itemId":3},
        {"name":"todo1","status":false,"itemId":4},
        {"name":"todo1","status":false,"itemId":5},
        {"name":"todo1","status":false,"itemId":6},
        {"name":"todo1","status":false,"itemId":7},
        {"name":"todo1","status":false,"itemId":8},
        {"name":"todo1","status":false,"itemId":9},
        {"name":"todo1","status":false,"itemId":10},
        {"name":"todo1","status":false,"itemId":11}
    ],
    "pageable": {"pageNumber":0,"pageSize":10,"sort":{"sorted":false,"empty":true,"unsorted":true},"offset":0,"paged":true,"unpaged":false},
    "last": false,
    "totalPages": 2,
    "totalElements": 11,
    "size": 10,
    "number": 0,
    "first": true,
    "numberOfElements": 10,
    "sort": {"sorted":false,"empty":true,"unsorted":true},
    "empty": false
}
        """
                            )
                    )
            )
    })


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

    @Operation(
            summary = "Create a new to-do item",
            description = "Creates a new item in the to-do list. " +
                    "You must provide a `name` and a `status` (true or false). " +
                    "The `itemId` will be generated automatically."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Created item example",
                                    value = """
{
  "name": "todo1",
  "status": false,
  "itemId": 12
}
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request (validation error or other issue)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation error example",
                                    value = """
{
  "status": 400,
  "message": "Error creating item: Name is required"
}
                """
                            )
                    )
            )
    })

    @PostMapping
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemDTO itemDto) {
        try {
            Item newItem = new Item();
            newItem.setName(itemDto.getName());
            newItem.setStatus(itemDto.getStatus());

            Item savedItem = itemService.addItem(newItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (ItemCreationException e) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Error creating item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }



    @Operation(summary = "Get item by ID", description = "Retrieve a single to-do item by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Single item example",
                                    value = """
{
  "name": "todo1",
  "status": false,
  "itemId": 4
}
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error example",
                                    value = """
{
  "status": 404,
  "message": "Item not found with id 4"
}
                """
                            )
                    )
            )
    })

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

    @Operation(summary = "Update an item", description = "Update an existing to-do item by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Updated item example",
                                    value = """
{
  "name": "string",
  "status": true,
  "itemId": 3
}
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Not found example",
                                    value = """
{
  "status": 404,
  "message": "Item not found with id 3"
}
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request (invalid data)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error example",
                                    value = """
{
  "status": 400,
  "message": "Error updating item: name must not be blank"
}
                """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO updatedItem) {
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
