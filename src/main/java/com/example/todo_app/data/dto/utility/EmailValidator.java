//package com.example.todo_app.data.dto.utility;
//
//import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
//
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//
//public class EmailValidator {
//    public static String validateEmail(String mail){
//        try{
//            InternetAddress internetAddress = new InternetAddress(mail);
//            internetAddress.validate();
//            return internetAddress.getAddress();
//        }catch (AddressException e) {
//            throw new ToDoRunTimeException("Invalid email, " + mail);
//        }
//    }
//}
