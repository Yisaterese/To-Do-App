package com.example.todo_app.service;


import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.repository.TaskRepository;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.request.GetAllTasksByUserRequest;
import com.example.todo_app.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.dto.response.DeleteTaskResponse;
import com.example.todo_app.exception.InvalidDateException;
import com.example.todo_app.exception.NotTaskFoundException;
import com.example.todo_app.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.todo_app.dto.utility.Mapper.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
  //  @Override
//    public Task createTask(CreateTaskRequest createTaskRequest) {
//        //Task existingTask = taskRepository.findTaskByTaskUniqueNumber(createTaskRequest.getTaskUniqueNumber());
//       // validateTask(existingTask);
//       // validateExistingTask(createTaskRequest, existingTask);
//        Task newTask = new Task();
//        setTaskProperties(createTaskRequest, newTask);
//        taskRepository.save(newTask);
//        return newTask;
//
//    }

//    private static void validateExistingTask(CreateTaskRequest createTaskRequest, Task existingTask) {
//        if(existingTask.getTaskUniqueNumber().equals(createTaskRequest.getTaskUniqueNumber()))throw new AlreadyExistingTaskException("Task already exist");
//    }

//    private static void validateTask(Task existingTask) {
//        if(existingTask == null)throw new TaskNotFoundException("Task not found");
//    }

//    private static void setTaskProperties(CreateTaskRequest createTaskRequest, Task newTask) {
//        newTask.setTitle(createTaskRequest.getTitle());
//        newTask.setCreatorEmail(createTaskRequest.getCreatorEmail());
//      //  newTask.setDateCreated(LocalDate.now());
//        newTask.setTaskPriority(createTaskRequest.getTaskPriority());
//        newTask.setDescription(createTaskRequest.getDescription());
//       // newTask.setDueDate(formatDate(createTaskRequest.getEstimatedTime()));
//       // newTask.setEstimatedTime(formatDate(createTaskRequest.getDueDate()));
//        newTask.setTaskUniqueNumber(createTaskRequest.getTaskUniqueNumber());
//    }

    public static LocalDate formatDate(String createTaskRequest){
        if(createTaskRequest == null)throw  new InvalidDateException("Invalid date provided");
        return LocalDate.parse(createTaskRequest);
    }

    @Override
    public Task createTask(CreateTaskRequest createTaskRequest) {
        Task newTask = new Task();
        newTask.setTaskUniqueNumber(createTaskRequest.getTaskUniqueNumber());
        newTask.setTaskPriority(createTaskRequest.getTaskPriority());
        newTask.setUserName(createTaskRequest.getUserName());
        newTask.setTitle(createTaskRequest.getTitle());
        newTask.setDescription(createTaskRequest.getDescription());
        taskRepository.save(newTask);
        return newTask;
    }


    @Override
    public List<Task> findAllTasks(){
        return getAllTasks();
    }

    private List<Task> getAllTasks() {
        //if(tasks.isEmpty())throw new NotTaskFoundException("Your list of tasks is empty");
        return taskRepository.findAll();
    }



    @Override
    public DeleteAllTaskResponse deleteAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty())throw new NotTaskFoundException("No task found");
        taskRepository.deleteAll();
        return mapDeleteTasksResponse();
    }

    @Override
    public DeleteTaskResponse deleteTaskByUserName(DeleteTaskRequest upDateTaskRequest){
        Optional<Task> foundTask = Optional.ofNullable(taskRepository.findTaskByUserName(upDateTaskRequest.getUserName().strip()));
        if (foundTask.isEmpty())throw new TaskNotFoundException("Task not found");
        taskRepository.delete(foundTask.get());
        return mapDeleteTaskResponse();
    }

    @Override
    public List<Task> findAllTaskByUser(GetAllTasksByUserRequest getUserRequest) {
        List<Task> foundTasks = taskRepository.findAllTasksByUserName(getUserRequest.getUserName());
        if (foundTasks.isEmpty())throw new NotTaskFoundException("No task found");
        return foundTasks;
    }

    @Override
    public Task findTaskByUser(String userName) {
        Task foundTask = taskRepository.findTaskByUserName(userName);
        if(foundTask == null)throw new TaskNotFoundException("Task not found");
        return foundTask;
    }


}
