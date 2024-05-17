package com.example.todo_app.data.model;

import com.example.todo_app.data.dto.utility.TaskStatus;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    private String userId;
    private LocalDate dueDate;
    private LocalDate dateCreated;


}



