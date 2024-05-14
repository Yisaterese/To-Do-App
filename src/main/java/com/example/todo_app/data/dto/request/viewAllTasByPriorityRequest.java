package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class viewAllTasByPriorityRequest {
    private String userId;
    private String taskPriority;
}
