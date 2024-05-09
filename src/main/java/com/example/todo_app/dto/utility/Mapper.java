package com.example.todo_app.dto.utility;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.dto.response.*;

import java.time.LocalDate;

public class Mapper {
    public static RegisterUserResponse mapRegisterResponse(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUserName(user.getUserName());
        registerUserResponse.setEmail(user.getEmail());
        registerUserResponse.setDateOFBirth(LocalDate.now());
        registerUserResponse.setPhoneNumber(user.getPhoneNumber());
        registerUserResponse.setUserAddress(user.getUserAddress());
        return registerUserResponse;
    }

    public static CreateTaskResponse mapCreateTaskResponse(Task task) {
        CreateTaskResponse response = new CreateTaskResponse();
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setTaskPriority(task.getTaskPriority());
        return response;
    }

    public static DeleteUserResponse mapDeleteUserResponse(){
        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        deleteUserResponse.setMessage("User deleted successfully");
        return deleteUserResponse;
    }

    public static DeleteAllTaskResponse mapDeleteTasksResponse(){
        DeleteAllTaskResponse response = new DeleteAllTaskResponse();
        response.setMessage("Tasks deleted successfully");
        return response;
    }

    public static UpdateUserResponse mapUpdateUserResponse(User user){
       UpdateUserResponse updateUserResponse = new UpdateUserResponse();
       updateUserResponse.setUserName(user.getUserName());
       updateUserResponse.setPhoneNumber(user.getPhoneNumber());
       updateUserResponse.setEmail(user.getEmail());
       updateUserResponse.setUserAddress(user.getUserAddress());
       updateUserResponse.setDateOFBirth(LocalDate.now());
       return updateUserResponse;
    }

    public static UpDateTaskResponse mapUDateTaskResponse(){
        UpDateTaskResponse upDateTaskResponse = new UpDateTaskResponse();
        upDateTaskResponse.setMessage("Task updated successfully");
        return upDateTaskResponse;
    }

    public static DeleteTaskResponse  mapDeleteTaskResponse(){
        DeleteTaskResponse deleteTaskResponse = new DeleteTaskResponse();
        deleteTaskResponse.setMessage("Task deleted successfully");
        return deleteTaskResponse;
    }

    public static LoginResponse mapLoginResponse(User existingUser){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoginStatus(existingUser.isLoggedIn());
        loginResponse.setMessage("User logged in successfully");
        return loginResponse;
    }

    public static LogOutResponse mapLogOutResponse(User existingUser){
        LogOutResponse logOutResponse = new LogOutResponse();
        logOutResponse.setMessage("User logged out successfully");
        logOutResponse.setLogOutStatus(existingUser.isLoggedIn());
        return logOutResponse;
    }

    public static AssignTaskResponse mapAssignTaskResponse(User assigner,User assignee){
        AssignTaskResponse assignTaskResponse = new AssignTaskResponse();
        assignTaskResponse.setMessage(assigner.getUserName()+" has successfully assigned task to "+assignee.getUserName());
        return assignTaskResponse;
    }

    public static DeleteAllUserResponse  mapDeleteAllUsersResponse(){
        DeleteAllUserResponse deleteAlluserResponse = new DeleteAllUserResponse();
        deleteAlluserResponse.setMessage("All users have been deleted successfully ");
        return deleteAlluserResponse;
    }
}
