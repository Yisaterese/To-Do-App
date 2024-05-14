package com.example.todo_app.data.dto.request;

import com.example.todo_app.data.dto.utility.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String userId;
    private String userName;
    private String password;
    private LocalDate dateOFBirth;
    private String email;
    private String phoneNumber;
    private Address userAddress;
}
