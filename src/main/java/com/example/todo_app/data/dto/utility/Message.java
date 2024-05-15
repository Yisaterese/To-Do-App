package com.example.todo_app.data.dto.utility;

import com.example.todo_app.data.dto.request.ShareTaskMessageRequest;
import com.example.todo_app.data.model.User;

public class Message {


    public static String registrationMessageSubject(){
        return "Registration Message";
    }
    public static String registrationMessageBody(String userName){
        return """
                   Hi %s ,
                   Welcome to our esteemed app where user concern is our utmost priority.
                   Thank you for choosing our service..
                """.formatted(userName);
    }

    public static String loginSubjectMessage(){
        return "Logged in successfully...";
    }

    public static String loginBodyMessage(){
        return "Welcome you can continue from where you stopped";
    }
    public static String shareTaskSubjectMessage(){
        return "Shared task ";
    }
    public static String   shareTaskMessage(String title,String username){
        return "The task "+title+" has been successfully shared with "+username;
    }

    public static String assignTaskMessage(String title,String username){
        return "The task "+title+" has been successfully assigned to "+username;
    }

    public static  String assigneTaskSubject(){
        return "You have been assigned a task";
    }
}
