package com.example.todo_app.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
    private String email;
}
