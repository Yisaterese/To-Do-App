package com.example.todo_app.service;


import com.example.todo_app.data.dto.request.UpDateTaskRequest;
import com.example.todo_app.data.dto.request.viewAllTasksByUserRequest;
import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.TaskRepository;
import com.example.todo_app.data.dto.request.CreateTaskRequest;
import com.example.todo_app.data.dto.request.DeleteTaskRequest;
import com.example.todo_app.data.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.data.dto.response.DeleteTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.todo_app.data.dto.utility.Mapper.mapDeleteTaskResponse;
import static com.example.todo_app.data.dto.utility.Mapper.mapDeleteTasksResponse;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Override
    public Task createTask(CreateTaskRequest createTaskRequest) {
        return getNewTask(createTaskRequest);
    }
    @Override
    public DeleteTaskResponse deleteTaskByUser(DeleteTaskRequest deleteTaskRequest){
      Task existingTask =  taskRepository.findAll().stream().filter(task -> task.getTitle().equals(deleteTaskRequest.getTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));
      if (existingTask == null)throw new ToDoRunTimeException("Task not found");
      taskRepository.delete(existingTask);
        return mapDeleteTaskResponse();
    }
    @Override
    public List<Task> findAllTaskByUser(viewAllTasksByUserRequest getUserRequest) {
        List<Task> foundTasks = taskRepository.findTasksByUserId(getUserRequest.getUserId());
        if (foundTasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return foundTasks;
    }
    @Override
    public List<Task> findAllTaskByUser(String userId) {
        List<Task> foundTasks = taskRepository.findTasksByUserId(userId);
        if (foundTasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return foundTasks;
    }
    @Override
    public Task findTaskById(String userId) {
        Task foundTask = taskRepository.findTaskByUserId(userId);
        if(foundTask == null)throw new ToDoRunTimeException("Task not found");
        return foundTask;
    }
    @Override
    public void deleteAllUserTask(List<Task> tasks) {
        taskRepository.deleteAll(tasks);
    }
    private Task getNewTask(CreateTaskRequest createTaskRequest) {
        Task newTask = new Task();
        setTaskProperties(createTaskRequest, newTask);
        return newTask;
    }
    private void setTaskProperties(CreateTaskRequest createTaskRequest, Task newTask) {
        newTask.setTaskPriority(createTaskRequest.getTaskPriority().trim());
        newTask.setUserId(createTaskRequest.getUserId());
        newTask.setTitle(createTaskRequest.getTitle().toLowerCase().trim());
        newTask.setDescription(createTaskRequest.getDescription());
        setDate(createTaskRequest, newTask);
        taskRepository.save(newTask);
    }

    private static void setDate(CreateTaskRequest createTaskRequest, Task newTask) {
        newTask.setDateCreated(LocalDate.now());
        newTask.setDueDate(formatDate(createTaskRequest.getDueDate()));
    }

    private static LocalDate formatDate(String createTaskRequest){
        if(createTaskRequest == null)throw  new ToDoRunTimeException("Invalid date provided");
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(createTaskRequest);
    }


    @Override
    public List<Task> findAllTasks(){
        return getAllTasks();
    }
    private List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTasksByUser(String id) {
        return taskRepository.findTasksByUserId(id);
    }

@Override
public Task updateUserTask(UpDateTaskRequest upDateTaskRequest, User existingUser) {
    final Task taskTobe_updated = getTaskTo_toBeUpdated(upDateTaskRequest, existingUser);
    updateTaskProperties(upDateTaskRequest, taskTobe_updated);
    taskRepository.save(taskTobe_updated);
    return taskTobe_updated;

}

    private static void updateTaskProperties(UpDateTaskRequest upDateTaskRequest, Task taskTobe_updated) {
        taskTobe_updated.setTaskPriority(upDateTaskRequest.getTaskPriority());
        taskTobe_updated.setTitle(upDateTaskRequest.getTitle());
        taskTobe_updated.setDueDate(formatDate(upDateTaskRequest.getDueDate()));
        taskTobe_updated.setDescription(upDateTaskRequest.getDescription());
    }

    private static Task getTaskTo_toBeUpdated(UpDateTaskRequest upDateTaskRequest, User existingUser) {
        Task taskTobe_updated = existingUser.getAllTasks().stream().filter(task -> task.getTitle().equals(upDateTaskRequest.getTaskToBeUpdatedTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));
        if(taskTobe_updated == null)throw new ToDoRunTimeException("Task not found");
        return taskTobe_updated;
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty())throw new ToDoRunTimeException("No task found");
        taskRepository.deleteAll();
        return mapDeleteTasksResponse();
    }
}
