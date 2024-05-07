package com.example.todo_app.dto.request;

import lombok.Data;

@Data
public class AssignTaskRequest {
    private String assigneeEmail;
    private String assignerEmail;
    private String taskToBeAssigned;
}
