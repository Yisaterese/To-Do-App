package com.example.todo_app.exception;

public class NotTaskFoundException extends ToDoRunTimeException {
    public NotTaskFoundException(String string) {
        super(string);
    }
}
