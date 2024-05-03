package com.example.todo_app.data.model;

import com.example.todo_app.dto.utility.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate DateCreated;
    private TaskPriority taskPriority;
    private User Creator;
    private String reminder;
    private LocalDate estimatedTime;
    private Object assignee;
}
