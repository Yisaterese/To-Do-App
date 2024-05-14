package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class AssignTaskRequest {
    private String assigneeUserId;
    private String assignerUserId;
    private String title;
}
