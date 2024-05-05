package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.request.*;
import com.example.todo_app.dto.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    List<Task> displayAllTasks();

    DeleteAllTaskResponse deleteAllTasks();

    DeleteTaskResponse deleteTaskByTId(DeleteTaskRequest deleteTaskRequest);

    LoginResponse login(LoginRequest loginRequest);

    LogOutResponse logOut(LogOutRequest logOutRequest);
}
