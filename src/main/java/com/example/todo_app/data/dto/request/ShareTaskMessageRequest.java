package com.example.todo_app.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareTaskMessageRequest {
    private String userName;
    private String title;
    private String email;
}
