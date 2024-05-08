package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.request.GetAllTasksByUserRequest;
import com.example.todo_app.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.dto.response.DeleteTaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
  Task createTask(CreateTaskRequest createTaskRequest);
    List<Task> findAllTasks();
    DeleteAllTaskResponse deleteAllTasks();
    DeleteTaskResponse deleteTaskByUserName(DeleteTaskRequest deleteTaskRequest);
  List<Task> findAllTaskByUser(GetAllTasksByUserRequest getUserRequest);
  Task findTaskByUser(String userName);
}
