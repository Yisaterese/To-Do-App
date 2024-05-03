package com.example.todo_app.exception;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

public class ToDoRunTimeException extends RuntimeException{
    public ToDoRunTimeException(String message){
        super(message);

    }

}
