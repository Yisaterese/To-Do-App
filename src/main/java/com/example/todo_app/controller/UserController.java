package com.example.todo_app.controller;

import com.example.todo_app.data.dto.request.*;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            RegisterUserResponse response = userService.registerUser(registerUserRequest);
            return  new ResponseEntity<>(new ApiResponse(true,response), HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login_user")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse response = userService.login(loginRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
}
    @PostMapping("/logOut_user")
    public ResponseEntity<ApiResponse> logOut(@RequestBody LogOutRequest logOutRequest){
        try{
            LogOutResponse response = userService.logOut(logOutRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/updateUser_profile")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateUserRequest updateUserRequest){
        try{
            UpdateUserResponse response = userService.updateUserProfile(updateUserRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }











//    @GetMapping("/getUser")
//    public ResponseEntity<?> getUser(@RequestBody GetUserRequest getUserRequest){
//        try{
//            User user = userService.getUserByEmail(getUserRequest);
//            return new ResponseEntity<>(new ApiResponse(true,user),HttpStatus.OK);
//            }catch (ToDoRunTimeException exception){
//            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/getAllUsers")
//    public ResponseEntity<?> getAllUsers(){
//        try {
//            List<User> allUsers = userService.getAllUsers();
//            return  new ResponseEntity<>(new ApiResponse(true,allUsers),HttpStatus.OK);
//        }catch (ToDoRunTimeException exception){
//            return  new ResponseEntity<>( new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/deleteAllUsers")
//    public ResponseEntity<?> deleteAllUsers(){
//        try {
//            DeleteAllUserResponse response = userService.deleteAllUsers();
//            return new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
//        }catch (ToDoRunTimeException exception){
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
//        }
//
//    }


}
