package com.example.todo_app.exception;

public class AlreadyExistingUserException extends ToDoRunTimeException {
    public AlreadyExistingUserException(String string) {
        super(string);
    }
}
