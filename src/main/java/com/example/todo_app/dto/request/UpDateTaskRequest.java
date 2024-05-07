package com.example.todo_app.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpDateTaskRequest {
    private String title;
    private String description;
    private String dueDate;
    private LocalDate dateCreated;
    private String taskPriority;
    private String creator;
    private String reminder;
    private String estimatedTime;
    private String assignee;
    private String TId;
}
