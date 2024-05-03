package com.example.todo_app.dto.utility;

import lombok.Data;

@Data
public class ContactInfo {
    private String email;
    private String phoneNumber;
    private Address userAddress;
}
