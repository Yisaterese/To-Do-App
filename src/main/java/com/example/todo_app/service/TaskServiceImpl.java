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
        List<Task> foundTasks = taskRepository.findByUserId(getUserRequest.getUserId());
        if (foundTasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return foundTasks;
    }
    @Override
    public List<Task> findAllTaskByUser(String userId) {
        List<Task> foundTasks = taskRepository.findByUserId(userId);
        if (foundTasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return foundTasks;
    }
    @Override
    public Task findTaskById(String userId) {
        Task foundTask = taskRepository.findTaskByUserId(userId);
        return validateIfTaskIsNull(foundTask);
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
       // newTask.setDueDate(formatDate(createTaskRequest.getDueDate()));
    }

    private static LocalDate formatDate(String createTaskRequest){
        if(createTaskRequest == null)throw  new ToDoRunTimeException("Invalid date provided");
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
        return taskRepository.findByUserId(id);
    }

@Override
public Task updateUserTask(UpDateTaskRequest upDateTaskRequest, User existingUser) {
    Task taskTobe_updated = getTask(existingUser.getAllTasks(), upDateTaskRequest.getTaskToBeUpdatedTitle());
    setUpdateTask_properties(upDateTaskRequest, taskTobe_updated);
    return taskRepository.save(taskTobe_updated);

}

    private static void setUpdateTask_properties(UpDateTaskRequest upDateTaskRequest, Task taskTobe_updated) {
        taskTobe_updated.setTaskPriority(upDateTaskRequest.getTaskPriority());
        taskTobe_updated.setDescription(upDateTaskRequest.getDescription());
        taskTobe_updated.setDueDate(formatDate(upDateTaskRequest.getDueDate()));
        taskTobe_updated.setTitle(upDateTaskRequest.getTitle().toLowerCase().trim());
    }

    private Task getTask(List<Task> allTasks, String title) {
        return allTasks.stream().filter(task -> task.getTitle().equals(title)).findFirst().orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    private void updateTaskPropertiesInRepository(UpDateTaskRequest upDateTaskRequest, User existingUser) {
        Task UserTaskToBe_updated = taskRepository.findTaskByUserId(existingUser.getId());
        validateIfTaskIsNull(UserTaskToBe_updated);
        updateTaskProperties(upDateTaskRequest, UserTaskToBe_updated);
        taskRepository.save( UserTaskToBe_updated);
    }

    private static void updateTaskProperties(UpDateTaskRequest upDateTaskRequest, Task taskTobe_updated) {
        taskTobe_updated.setTaskPriority(upDateTaskRequest.getTaskPriority());
        taskTobe_updated.setTitle(upDateTaskRequest.getTaskToBeUpdatedTitle());
        taskTobe_updated.setDueDate(formatDate(upDateTaskRequest.getDueDate()));
        taskTobe_updated.setDescription(upDateTaskRequest.getDescription());
    }

    private static Task getTaskTo_toBeUpdated(UpDateTaskRequest upDateTaskRequest, User existingUser) {
        Task taskTobe_updated = existingUser.getAllTasks().stream().filter((task) -> task.getTitle().equals(upDateTaskRequest.getTaskToBeUpdatedTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));
        return validateIfTaskIsNull(taskTobe_updated);
    }

    private static Task validateIfTaskIsNull(Task taskTobe_updated) {
        if(taskTobe_updated == null)throw new ToDoRunTimeException("Task not found");
        return taskTobe_updated;
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasks(User user) {
        List<Task> tasks = taskRepository.findByUserId(user.getId());
        if(tasks.isEmpty())throw new ToDoRunTimeException("No task found");
        taskRepository.deleteAll(tasks);
        return mapDeleteTasksResponse();
    }
}
