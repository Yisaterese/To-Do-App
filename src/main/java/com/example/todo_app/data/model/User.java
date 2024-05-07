package com.example.todo_app.data.model;

import com.example.todo_app.dto.utility.Address;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class User {
    private String userName;
    private String password;
    private LocalDate dateOFBirth;
    @Id
    private String email;
    private String phoneNumber;
    private boolean isLogIn;
    private Address userAddress;
    private List<Task> allTasks = new ArrayList<>();
    private List<Task> completedTasks = new ArrayList<>();
    private List<Task> pendingTasks = new ArrayList<>();
}
