package com.example.todo_app.dto.request;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.utility.ContactInfo;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreatTaskRequest {
    private String userName;
    private String password;
    private ContactInfo contact;
    private LocalDate dateOFBirth;
    private List<Task> allTasks = new ArrayList<>();
    private List<Task> completedTasks = new ArrayList<>();
    private List<Task> pendingTasks = new ArrayList<>();
}
