package com.example.todo_app.data.dto.request;

import lombok.Data;

@Data
public class GetUserPendingTasksRequest {
    private String userId;
}
