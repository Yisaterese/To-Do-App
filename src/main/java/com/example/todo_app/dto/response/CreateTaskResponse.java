package com.example.todo_app.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskResponse {
    private String title;
    private String description;
    private String taskPriority;
    private String taskUniqueNumber;
    private String userName;
    private LocalDate dueDate;
    private LocalDate DateCreated;



}
