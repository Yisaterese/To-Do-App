package com.example.todo_app.exception;

public class TaskNotFoundException extends ToDoRunTimeException{
    public TaskNotFoundException(String string) {
        super(string);
    }
}
