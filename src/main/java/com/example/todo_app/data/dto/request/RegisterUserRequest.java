package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String password;
    private String userName;

}
