package com.example.todo_app.data.dto.utility;

public class Message {


    public static String registrationMessageSubject(){
        return "Welcome message...";
    }
    public static String registrationMessageBody(String message){
        return """
                   Hi %s,
                   Welcome to our esteemed app where user concern is our utmost priority.
                   Thank you for choosing our service..
                """.formatted(message);
    }

    public static String loginSubjectMessage(){
        return "Logged in...";
    }

    public static String loginBodyMessage(){
        return "Welcome you can continue from where you stopped";
    }

}
