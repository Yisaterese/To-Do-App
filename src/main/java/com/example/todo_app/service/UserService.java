package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.request.RegisterUserRequest;
import com.example.todo_app.dto.response.CreateTaskResponse;
import com.example.todo_app.dto.response.DeleteTaskResponse;
import com.example.todo_app.dto.response.RegisterUserResponse;
import com.example.todo_app.dto.response.DeleteAllTaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    List<Task> displayAllTasks();

    DeleteAllTaskResponse deleteAllTasks();

    DeleteTaskResponse deleteTaskByTId(DeleteTaskRequest deleteTaskRequest);
}
