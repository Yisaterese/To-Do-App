package com.example.todo_app.data.dto.utility;

import com.example.todo_app.data.dto.response.SendMailResponse;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;

public class Mapper {
    public static RegisterUserResponse mapRegisterResponse(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUserId(user.getId());
        registerUserResponse.setMessage("Registered successfully.");
        return registerUserResponse;
    }

    public static CreateTaskResponse mapCreateTaskResponse(Task task) {
        CreateTaskResponse response = new CreateTaskResponse();
        response.setMessage("Task created successfully");
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
       updateUserResponse.setMessage("profile updated successfully");

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
        loginResponse.setMessage("User logged in successfully");
        return loginResponse;
    }

    public static LogOutResponse mapLogOutResponse(User existingUser){
        LogOutResponse logOutResponse = new LogOutResponse();
        logOutResponse.setMessage("User logged out successfully");
        return logOutResponse;
    }

    public static AssignTaskResponse mapAssignTaskResponse(User assigner,User assignee){
        AssignTaskResponse assignTaskResponse = new AssignTaskResponse();
        assignTaskResponse.setMessage(assigner.getUserName()+" has successfully assigned task to "+assignee.getUserName());
        return assignTaskResponse;
    }

    public static ShareTaskResponse  mapShareTaskResponse(){
        ShareTaskResponse shareTaskResponse = new ShareTaskResponse();
        shareTaskResponse.setMessage("Task shared successfully");
        return shareTaskResponse;
    }

    public static DeleteAllUserResponse mapDeleteAllUsersResponse(){
        DeleteAllUserResponse deleteAlluserResponse = new DeleteAllUserResponse();
        deleteAlluserResponse.setMessage("All users have been deleted successfully ");
        return deleteAlluserResponse;
    }

    public static SendMailResponse mapSendEmailResponse(){
        SendMailResponse sendMailResponse = new SendMailResponse();
        sendMailResponse.setMessage("Mail sent successfully");
        return sendMailResponse;
    }


}
