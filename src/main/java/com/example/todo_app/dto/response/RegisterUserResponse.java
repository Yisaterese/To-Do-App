package com.example.todo_app.dto.response;

import com.example.todo_app.dto.utility.ContactInfo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserResponse {
    private String userName;
    private ContactInfo contact;
    private LocalDate dateOFBirth;
}
