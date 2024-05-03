package com.example.todo_app.service;

import com.example.todo_app.dto.request.RegisterUserRequest;
import com.example.todo_app.dto.response.RegisterUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest);
}
