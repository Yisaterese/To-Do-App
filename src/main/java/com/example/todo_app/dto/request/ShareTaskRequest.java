package com.example.todo_app.dto.request;

import lombok.Data;

@Data
public class ShareTaskRequest {
    private String assignerUserName;
    private String assigneeUserName;
    private String title;
}
