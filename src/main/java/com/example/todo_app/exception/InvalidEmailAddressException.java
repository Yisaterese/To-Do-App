package com.example.todo_app.exception;

public class InvalidEmailAddressException extends ToDoRunTimeException {
    public InvalidEmailAddressException(String string) {
        super(string);
    }
}
