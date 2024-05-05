package com.example.todo_app.dto.request;

import com.example.todo_app.dto.utility.Address;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private Address userAddress;
}