package com.example.todo_app.exception;

public class ExistingUserException extends ToDoRunTimeException {
    public ExistingUserException(String message) {
        super(message);
    }
}
