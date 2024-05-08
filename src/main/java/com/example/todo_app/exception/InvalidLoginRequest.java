package com.example.todo_app.exception;

public class InvalidLoginRequest extends ToDoRunTimeException {
    public InvalidLoginRequest(String message) {
        super(message);
    }
}
