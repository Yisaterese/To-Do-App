package com.example.todo_app.controller;

import com.example.todo_app.data.dto.request.*;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    UserService userService;
    @PostMapping("/createTask")
    public ResponseEntity<ApiResponse> createTask(@RequestBody CreateTaskRequest createTaskRequest){
        try{
            CreateTaskResponse response = userService.createTask(createTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        }   catch (ToDoRunTimeException exception){
            return  new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/viewAllTasksByUser")
    public ResponseEntity<ApiResponse> viewAllTaskByUser(@RequestBody viewAllTasksByUserRequest viewAllTasksByUserrequest) {
        try {
            List<Task> response = userService.getUserTasks(viewAllTasksByUserrequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteAllTaskByUser")
    public ResponseEntity<ApiResponse> deleteAllTasksByUser(@RequestBody DeleteAllTaskByUserRequest deleteAllTaskRequest) {
        try {
            DeleteAllTaskResponse response = userService.deleteAllTasksByUser(deleteAllTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/assignTask")
    public ResponseEntity<ApiResponse> assignTask(@RequestBody AssignTaskRequest assignTaskRequest){
        try{
            AssignTaskResponse response = userService.assignTask(assignTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/shareTask")
    public ResponseEntity<ApiResponse> shareTask(@RequestBody ShareTaskRequest shareTaskRequest){
        try{
            ShareTaskResponse response = userService.shareTask(shareTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/viewTask_byPriority")
    public ResponseEntity<ApiResponse> viewAllTaskByPriority(@RequestBody viewAllTasByPriorityRequest viewAllTasByPriorityRequest){
        try{
            List<Task> response = userService.findUserTasksByPriority(viewAllTasByPriorityRequest);
            return  new ResponseEntity<>(new ApiResponse(true, response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/updateTask")
    public ResponseEntity<ApiResponse> updateTask(@RequestBody UpDateTaskRequest upDateTaskRequest){
        try{
            UpDateTaskResponse response = userService.updateTask(upDateTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
}
    @PostMapping("/view_task")
    public ResponseEntity<ApiResponse> viewTask(@RequestBody GetTaskRequest getTaskRequest){
        try{
            Task task = userService.findTaskByTitle(getTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,task),HttpStatus.OK);
        }catch (ToDoRunTimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/viewAll_pendingTask")
    public ResponseEntity<ApiResponse> viewPendingTask(@RequestBody  GetUserPendingTasksRequest getAllPendingTasksRequests ) {
        try {
            List<Task> task = userService.viewAllPendingTasksByUser(getAllPendingTasksRequests);
            return new ResponseEntity<>(new ApiResponse(true, task), HttpStatus.OK);
        } catch (ToDoRunTimeException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/complete_task")
    public ResponseEntity<ApiResponse> completeTask (@RequestBody CompleteTaskRequest completedTaskRequest){
            try {
                CompleteTaskResponse response = userService.completeTask(completedTaskRequest);
                return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
            } catch (ToDoRunTimeException exception) {
                return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
}

