package com.example.todo_app.dto;

import com.example.todo_app.data.model.User;
import com.example.todo_app.dto.response.RegisterUserResponse;

public class Mapper {
    public static RegisterUserResponse mapRegisterResponse(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUserName(user.getUserName());
        registerUserResponse.setContact(user.getContact());
        registerUserResponse.setDateOFBirth(user.getDateOFBirth());
        return registerUserResponse;
    }
}
