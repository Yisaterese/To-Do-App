package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.UserRepository;
import com.example.todo_app.dto.request.*;
import com.example.todo_app.dto.response.*;
import com.example.todo_app.exception.UserAlreadyLoggedException;
import com.example.todo_app.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.todo_app.dto.utility.Mapper.*;

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
        setPropertiesForUser(createTaskRequest, newuser);
        userRepository.save(newuser);
        return mapRegisterResponse(newuser);
    }

    private static void setPropertiesForUser(RegisterUserRequest createTaskRequest, User newuser) {
        newuser.setUserName(createTaskRequest.getUserName());
        newuser.setPassword(createTaskRequest.getPassword());
        newuser.setEmail(createTaskRequest.getEmail());
        newuser.setDateOFBirth(LocalDate.now());
        newuser.setUserAddress(createTaskRequest.getUserAddress());
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
        User existingUser = userRepository.findUserByEmail(loginRequest.getEmail());
        //validateUserByEmail(loginRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        setUserProperties(loginRequest, existingUser);
        userRepository.save(existingUser);
        return  mapLoginResponse(existingUser);
    }

    private  void setUserProperties(LoginRequest loginRequest, User existingUser) {
        existingUser.setEmail(loginRequest.getEmail());
        existingUser.setUserName(loginRequest.getUserName());
        existingUser.setPassword(loginRequest.getPassword());
        existingUser.setLogIn(true);
    }

    private static void validateAlreadyLoggedIn(User existingUser) {
        if(existingUser.isLogIn())throw new UserAlreadyLoggedException("User already logged in ");
    }

    private static void validateUserByEmail(LoginRequest loginRequest, User existingUser) {
        if(!existingUser.getEmail().equals(loginRequest.getEmail()))throw new UserNotFoundException("user not found");
    }

    private static void validateIfIsNull(User existingUser) {
        if(existingUser == null)throw new UserNotFoundException("User not found");
    }

   @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest){
        User existingUser = getUserByEmail(logOutRequest);
        //validateUserEmail(logOutRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        existingUser.setLogIn(false);
        userRepository.save(existingUser);
        return mapLogOutResponse(existingUser);


    }

    private User getUserByEmail(LogOutRequest logOutRequest) {
        return userRepository.findUserByEmail(logOutRequest.getEmail());
    }

    private static void validateUserEmail(LogOutRequest logOutRequest, User existingUser) {
        if(!existingUser.getEmail().equals(logOutRequest.getEmail()))throw new UserNotFoundException("user not found");
    }

}
