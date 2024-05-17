package com.example.todo_app.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@Document
public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private boolean isLoggedIn;
    @DBRef
    private List<Task> allTasks = new ArrayList<>();
    @DBRef
    private List<Task> completedTasks = new ArrayList<>();
    @DBRef
    private List<Task> pendingTasks = new ArrayList<>();
}
