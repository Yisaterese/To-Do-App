package com.example.todo_app.service;

import com.example.todo_app.data.dto.request.UpDateTaskRequest;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.dto.request.CreateTaskRequest;
import com.example.todo_app.data.dto.request.DeleteTaskRequest;
import com.example.todo_app.data.dto.request.viewAllTasksByUserRequest;
import com.example.todo_app.data.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.data.dto.response.DeleteTaskResponse;
import com.example.todo_app.data.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
  Task createTask(CreateTaskRequest createTaskRequest);
    List<Task> findAllTasks();
Task updateUserTask(UpDateTaskRequest upDateTaskRequest, User existingUser);

  DeleteAllTaskResponse deleteAllTasks();
    DeleteTaskResponse deleteTaskByUser(DeleteTaskRequest deleteTaskRequest);
  List<Task> findAllTaskByUser(viewAllTasksByUserRequest getUserRequest);
  List<Task> findAllTaskByUser(String userid);
  Task findTaskById(String userId);
  void deleteAllUserTask(List<Task> tasks);
  List<Task> getAllTasksByUser(String id);
}
