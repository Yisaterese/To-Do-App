package com.example.todo_app.controller;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.request.RegisterUserRequest;
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

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(CreateTaskRequest createTaskRequest){
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

    @DeleteMapping("/deleteTaskByTId")
    public ResponseEntity<?> deleteTaskByTId(DeleteTaskRequest deleteTaskRequest) {
        try {
            DeleteTaskResponse response = userService.deleteTaskByTId(deleteTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
