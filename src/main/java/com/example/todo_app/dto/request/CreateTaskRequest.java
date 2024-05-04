package com.example.todo_app.dto.request;
import com.example.todo_app.data.model.User;
import com.example.todo_app.dto.utility.TaskPriority;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate DateCreated;
    private String  taskPriority;
    private String TId;
    private String reminder;
    private LocalDate estimatedTime;
    private Object assignee;
    private User Creator;
}
