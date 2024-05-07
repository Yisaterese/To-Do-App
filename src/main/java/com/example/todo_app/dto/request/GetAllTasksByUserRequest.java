package com.example.todo_app.dto.request;

import lombok.Data;

@Data
public class GetAllTasksByUserRequest {
    private String userName;
}
