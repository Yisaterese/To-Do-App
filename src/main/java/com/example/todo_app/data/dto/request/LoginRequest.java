package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class    LoginRequest {
    private String userId;
    private String password;
}
