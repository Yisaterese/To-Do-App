package com.example.todo_app.data.dto.request;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private String taskPriority;
    private String userId;
    private String dueDate;

}
