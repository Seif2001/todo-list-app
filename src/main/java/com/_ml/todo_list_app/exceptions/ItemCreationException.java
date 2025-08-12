package com._ml.todo_list_app.exceptions;

public class ItemCreationException extends RuntimeException {
    public ItemCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

