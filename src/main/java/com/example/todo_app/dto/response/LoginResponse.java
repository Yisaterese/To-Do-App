package com.example.todo_app.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private boolean loginStatus;
}
