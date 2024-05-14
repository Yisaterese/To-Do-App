package com.example.todo_app.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSenderRequest {
     private String to;
    private String subject;
    private String body;
}
