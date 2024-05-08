package com.example.todo_app.dto.response;

import com.example.todo_app.dto.utility.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class    UpdateUserResponse {
    private String userName;
    private LocalDate dateOFBirth;
    private String email;
    private String phoneNumber;
    private Address userAddress;
}
