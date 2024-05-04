package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.response.CreateTaskResponse;
import com.example.todo_app.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.dto.response.DeleteTaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);
    List<Task> findAllTasks();
    DeleteAllTaskResponse deleteAllTasks();
    DeleteTaskResponse deleteTaskByTaskId(DeleteTaskRequest deleteTaskRequest);

}
