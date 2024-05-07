package com.example.todo_app.data.model;

import lombok.Data;


@Data
public class Task {
    private String title;
    private String description;
    private String taskPriority;
    private String userName;
    private String taskUniqueNumber;
    //private String dueDate;
    //private LocalDate dateCreated;
    //private String estimatedTime;

}
