package com.example.todo_app.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteTaskRequest {
    private String userId;
    private String title;
}
