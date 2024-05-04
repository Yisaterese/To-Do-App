package com.example.todo_app.service;


import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.repository.TaskRepository;
import com.example.todo_app.dto.request.CreateTaskRequest;
import com.example.todo_app.dto.request.DeleteTaskRequest;
import com.example.todo_app.dto.response.CreateTaskResponse;
import com.example.todo_app.dto.response.DeleteAllTaskResponse;
import com.example.todo_app.dto.response.DeleteTaskResponse;
import com.example.todo_app.exception.AlreadyExistingTaskException;
import com.example.todo_app.exception.NotTaskFoundException;
import com.example.todo_app.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.todo_app.dto.utility.Mapper.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        //taskRepository.findAll().forEach(task -> {if (createTaskRequest.getTitle().equals(task.getTitle())) throw new AlreadyExistingTaskException("Task already created");});
        Task newTask = new Task();
        newTask.setTitle(createTaskRequest.getTitle());
        newTask.setCreator(createTaskRequest.getCreator());
        newTask.setDateCreated(LocalDate.now());
        newTask.setTaskPriority(createTaskRequest.getTaskPriority());
        newTask.setDescription(createTaskRequest.getDescription());
        newTask.setAssignee(createTaskRequest.getAssignee());
        newTask.setReminder(createTaskRequest.getReminder());
        newTask.setDueDate(createTaskRequest.getDueDate());
        newTask.setEstimatedTime(createTaskRequest.getEstimatedTime());
        newTask.setTId(createTaskRequest.getTId());
        taskRepository.save(newTask);
        return mapCreateTaskResponse(newTask);
    }
    @Override
    public List<Task> findAllTasks(){
        final List<Task> tasks = getAllTasks();
        return tasks;
    }

    private List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty())throw new NotTaskFoundException("Your list of tasks is empty");
        return tasks;
    }



    @Override
    public DeleteAllTaskResponse deleteAllTasks() {
        taskRepository.deleteAll();
        return mapDeleteTasksResponse();
    }

    @Override
    public DeleteTaskResponse deleteTaskByTaskId(DeleteTaskRequest upDateTaskRequest){
        Optional<Task> foundTask = taskRepository.findById(upDateTaskRequest.getTId());
        if (foundTask.isEmpty())throw new TaskNotFoundException("Task not found");
        taskRepository.delete(foundTask.get());
        return mapDeleteTaskResponse();
    }


}
