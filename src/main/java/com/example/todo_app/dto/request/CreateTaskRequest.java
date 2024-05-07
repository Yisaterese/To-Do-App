package com.example.todo_app.dto.request;
import lombok.Data;


@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private String taskPriority;
    private String taskUniqueNumber;
    private String userName;
    //private String dueDate;
    //private LocalDate dateCreated;
    //private String estimatedTime;

}
