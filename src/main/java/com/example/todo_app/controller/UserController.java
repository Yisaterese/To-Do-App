package com.example.todo_app.controller;

import com.example.todo_app.dto.request.RegisterUserRequest;
import com.example.todo_app.dto.response.ApiResponse;
import com.example.todo_app.dto.response.RegisterUserResponse;
import com.example.todo_app.exception.ToDoRunTimeException;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
