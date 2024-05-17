package com.example.todo_app.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    private String userId;
    private String userNameTBeUpdated;
    private String passwordToBeUpdated;
    private String emailToBeUpdated;
}
