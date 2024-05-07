package com.example.todo_app.exception;

public class UserAlreadyLoggedOutException extends ToDoRunTimeException {
    public UserAlreadyLoggedOutException(String string) {
        super(string);
    }
}
