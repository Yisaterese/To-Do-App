package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
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

    List<Task> getUserTasks(GetAllTasksByUserRequest getUserRequest);

    DeleteTaskResponse deleteTaskByUserName(DeleteTaskRequest deleteTaskRequest);

    LoginResponse login(LoginRequest loginRequest);

    LogOutResponse logOut(LogOutRequest logOutRequest);

    AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest);

    User getUserByEmail(GetUserRequest getUserRequest);

    List<User> getAllUsers();

    DeleteAllUserResponse deleteAllUsers();

    User findUserByUserName(String userName);

    DeleteUserResponse deleteUserByUserName(DeleteUserRequest deleteUserRequest);

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

    Task findTaskByTitle(CreateTaskRequest createTaskRequest);

    ShareTaskResponse shareTask(ShareTaskRequest shareTaskRequest);
}
