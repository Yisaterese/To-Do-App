package com.example.todo_app.dto.utility;

import com.example.todo_app.exception.InvalidEmailAddressException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator {
    public static String validateEmail(String mail){
        try{
            InternetAddress internetAddress = new InternetAddress(mail);
            internetAddress.validate();
            return internetAddress.getAddress();
        }catch (AddressException e) {
            throw new InvalidEmailAddressException("Invalid email, " + mail);
        }
    }
}
