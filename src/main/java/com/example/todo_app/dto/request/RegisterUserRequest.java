package com.example.todo_app.dto.request;

import com.example.todo_app.dto.utility.ContactInfo;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String userName;
    private String password;
    private ContactInfo contact;
}
