package com.example.todo_app.exception;

public class AlreadyExistingTaskException extends ToDoRunTimeException {
    public AlreadyExistingTaskException(String string) {
        super(string);
    }
}
