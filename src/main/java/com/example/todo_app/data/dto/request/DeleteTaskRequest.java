package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class DeleteTaskRequest {
    private String userId;
    private String title;
}
