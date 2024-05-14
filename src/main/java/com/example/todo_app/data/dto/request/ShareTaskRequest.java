package com.example.todo_app.data.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShareTaskRequest {
    private String assignerId;
    private String assigneeId;
    private String title;

}
