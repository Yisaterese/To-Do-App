package com.example.todo_app.exception;

public class UserNotFoundException extends ToDoRunTimeException {
    public UserNotFoundException(String string) {
        super(string);
    }
}
