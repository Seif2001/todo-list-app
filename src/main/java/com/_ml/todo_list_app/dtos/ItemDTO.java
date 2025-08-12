package com._ml.todo_list_app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Status is required")
    private Boolean status;

    // Constructors
    public ItemDTO() {}

    public ItemDTO(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}
