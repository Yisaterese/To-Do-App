package com.example.todo_app.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Task {
    @Id
    private String title;
    private String description;
    private String taskPriority;
    private String userName;
    private String taskUniqueNumber;
    //private String dueDate;
    //private LocalDate dateCreated;
    //private String estimatedTime;

}



