package com.example.todo_app.data.repository;

import com.example.todo_app.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String > {
    Task findTaskByUserId(String userId);

    List<Task> findTasksByUserId(String userName);

}
