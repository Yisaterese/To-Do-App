package com.example.todo_app.data.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class viewAllTasByPriorityRequest {
    private String userId;
    private String taskPriority;
}
