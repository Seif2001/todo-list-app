package com._ml.todo_list_app.errors;
public class ApiError {
    private int status;
    private String message;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // getters and setters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
}

