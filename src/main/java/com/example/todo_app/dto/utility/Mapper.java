package com.example.todo_app.dto.utility;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.dto.response.*;

import java.time.LocalDate;

public class Mapper {
    public static RegisterUserResponse mapRegisterResponse(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUserName(user.getUserName());
        registerUserResponse.setPassword(user.getPassword());
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
        response.setDueDate(task.getDueDate());
        response.setDateCreated(task.getDateCreated());
        response.setTaskPriority(task.getTaskPriority());
        response.setCreator(task.getCreator());
        response.setAssignee(task.getAssignee());
        response.setReminder(task.getReminder());
        response.setEstimatedTime(task.getEstimatedTime());
        return response;
    }

    public static DeleteAllTaskResponse mapDeleteTasksResponse(){
        DeleteAllTaskResponse response = new DeleteAllTaskResponse();
        response.setMessage("Tasks deleted successfully");
        return response;
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
}
