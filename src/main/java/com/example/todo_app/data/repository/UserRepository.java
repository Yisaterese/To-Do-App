package com.example.todo_app.data.repository;

import com.example.todo_app.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserById(String userId);
    User findUserByUserName(String userName);
    boolean existsByEmail(String email);
    void deleteByUserName(String userName);
    User findByEmail(String email);
}
