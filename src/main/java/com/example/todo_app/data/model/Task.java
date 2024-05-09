package com.example.todo_app.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@Document
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String taskPriority;
    private String userName;
    private String taskUniqueNumber;
//    private LocalDate dueDate;
//    private LocalDate dateCreated;


}



