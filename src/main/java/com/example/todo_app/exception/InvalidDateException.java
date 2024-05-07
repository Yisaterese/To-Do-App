package com.example.todo_app.exception;

public class InvalidDateException extends ToDoRunTimeException {
    public InvalidDateException(String message) {
        super(message);
    }
}
