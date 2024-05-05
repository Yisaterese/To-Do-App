package com.example.todo_app.exception;

public class UserAlreadyLoggedException extends ToDoRunTimeException {
    public UserAlreadyLoggedException(String string) {
        super(string);
    }
}
