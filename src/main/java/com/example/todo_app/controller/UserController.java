package com.example.todo_app.controller;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.dto.request.*;
import com.example.todo_app.dto.response.*;
import com.example.todo_app.exception.ToDoRunTimeException;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            RegisterUserResponse response = userService.registerUser(registerUserRequest);
            return  new ResponseEntity<>(new ApiResponse(true,response), HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
@PostMapping("/login_user")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse response = userService.login(loginRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
}

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest createTaskRequest){
        try{
            CreateTaskResponse response = userService.createTask(createTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }   catch (ToDoRunTimeException exception){
            return  new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllTasks")
    public  ResponseEntity<?> getAllTasks(){
        try{
            List<Task> tasks = userService.displayAllTasks();
            return new ResponseEntity<>(new ApiResponse(true,tasks),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteAllTasks")
    public ResponseEntity<?> deleteAllTasks() {
        try {
            DeleteAllTaskResponse response = userService.deleteAllTasks();
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteTaskByUserName")
    public ResponseEntity<?> deleteTaskByUniqueNumber(@RequestBody DeleteTaskRequest deleteTaskRequest) {
        try {
            DeleteTaskResponse response = userService.deleteTaskByUserName(deleteTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/assignTask")
    public ResponseEntity<?> assignTask(AssignTaskRequest assignTaskRequest){
        try{
            AssignTaskResponse response = userService.assignTask(assignTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestBody GetUserRequest getUserRequest){
        try{
            User user = userService.getUserByEmail(getUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,user),HttpStatus.OK);
            }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser_tasks")
    public ResponseEntity<?> getUserTasks(@RequestBody GetAllTasksByUserRequest getUserRequest){
        try{
            List<Task> tasks = userService.getUserTasks(getUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,tasks),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<User> allUsers = userService.getAllUsers();
            return  new ResponseEntity<>(new ApiResponse(true,allUsers),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return  new ResponseEntity<>( new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers(){
        try {
            DeleteAllUserResponse response = userService.deleteAllUsers();
            return new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/logOut_user")
    public ResponseEntity<?> logOut(@RequestBody LogOutRequest logOutRequest){
        try{
            LogOutResponse response = userService.logOut(logOutRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_user")
    public ResponseEntity<?> update(@RequestBody UpdateUserRequest updateUserRequest){
        try{
           UpdateUserResponse response = userService.updateUser(updateUserRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
