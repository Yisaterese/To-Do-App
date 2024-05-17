package com.example.todo_app.service;

import com.example.todo_app.data.dto.request.*;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    DeleteAllTaskResponse deleteAllTasks();

    List<Task> getUserTasks(viewAllTasksByUserRequest getUserRequest);

    DeleteTaskResponse deleteTaskByUser(DeleteTaskRequest deleteTaskRequest);

    LoginResponse login(LoginRequest loginRequest);

    LogOutResponse logOut(LogOutRequest logOutRequest);

    AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest);

    User getUserByEmail(GetUserRequest getUserRequest);

    List<User> getAllUsers();

    User findUserByUserName(String userName);
    User findUserById(String id);

    DeleteUserResponse deleteUserByUserName(DeleteUserRequest deleteUserRequest);

    UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest);

    ShareTaskResponse shareTask(ShareTaskRequest shareTaskRequest);

    DeleteAllTaskResponse deleteAllTasksByUser(DeleteAllTaskByUserRequest deleteTaskByUserRequest);

    List<Task> getAllTaskSByUser(viewAllTasksByUserRequest viewAllTasksByUserrequest);

    CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest);
    List<Task> findUserTasksByPriority(viewAllTasByPriorityRequest getTaskByPriorityRequest);

    List<Task> viewAllPendingTasksByUser(GetUserPendingTasksRequest getAllPendingTasksRequests);


    Task findTaskByTitle(GetTaskRequest getTaskRequest);

    UpDateTaskResponse updateTask(UpDateTaskRequest upDateTaskRequest);
}
