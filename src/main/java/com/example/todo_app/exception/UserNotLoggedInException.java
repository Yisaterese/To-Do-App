package com.example.todo_app.exception;

public class UserNotLoggedInException extends ToDoRunTimeException {
    public UserNotLoggedInException(String userNotLoggedIn) {
        super(userNotLoggedIn);
    }
}
