package com.example.todo_app.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTaskRequest {
    private String userId;
    private String title;
}
