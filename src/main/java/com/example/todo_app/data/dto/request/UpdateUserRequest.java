package com.example.todo_app.data.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String userId;
    private String userName;
    private String password;
    private String email;
}
