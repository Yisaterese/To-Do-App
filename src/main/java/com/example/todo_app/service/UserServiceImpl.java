package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.UserRepository;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.request.LoginRequest;
import com.example.todo_app.dto.request.RegisterUserRequest;
import com.example.todo_app.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.todo_app.dto.utility.Mapper.mapRegisterResponse;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest){
     //  User isExistingUser = userRepository.findUserByEmail(createTaskRequest.getContact().getEmail());
        //userRepository.findAll().forEach(user -> {if (user.getContact().getEmail().equals(createTaskRequest.getContact().getEmail()))throw new AlreadyExistingUserException("User with name "+createTaskRequest.getUserName()+" already exists");});
        User newuser = new User();
        newuser.setUserName(createTaskRequest.getUserName());
        newuser.setPassword(createTaskRequest.getPassword());
        newuser.setEmail(createTaskRequest.getEmail());
        newuser.setDateOFBirth(LocalDate.now());
        newuser.setUserAddress(createTaskRequest.getUserAddress());
        userRepository.save(newuser);
        return mapRegisterResponse(newuser);
    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest){
        return taskService.createTask(createTaskRequest);
    }

    @Override
    public List<Task> displayAllTasks(){
        return taskService.findAllTasks();
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasks(){
        return taskService.deleteAllTasks();

    }

    @Override
    public DeleteTaskResponse deleteTaskByTId(DeleteTaskRequest deleteTaskRequest) {
        return taskService.deleteTaskByTaskId(deleteTaskRequest);
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){

    }

}
