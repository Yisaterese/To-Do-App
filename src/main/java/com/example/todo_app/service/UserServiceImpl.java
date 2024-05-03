package com.example.todo_app.service;

import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.UserRepository;
import com.example.todo_app.dto.request.RegisterUserRequest;
import com.example.todo_app.dto.response.RegisterUserResponse;
import com.example.todo_app.exception.AlreadyExistingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.todo_app.dto.Mapper.mapRegisterResponse;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest){
       // userRepository.findAll().forEach(user -> {if (user.getContact().getEmail().equals(createTaskRequest.getContact().getEmail()))throw new AlreadyExistingUserException("User with name "+createTaskRequest.getUserName()+" already exists");});
        User newuser = new User();
        newuser.setUserName(createTaskRequest.getUserName());
        newuser.setPassword(createTaskRequest.getPassword());
        newuser.setContact(createTaskRequest.getContact());
        newuser.setDateOFBirth(LocalDate.now());
        userRepository.save(newuser);
        return mapRegisterResponse(newuser);


    }
}
