package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class UpDateTaskRequest {
    private String title;
    private String description;
    private String dueDate;
    private String taskPriority;
    private String userId;
    private String taskToBeUpdatedTitle;
}
