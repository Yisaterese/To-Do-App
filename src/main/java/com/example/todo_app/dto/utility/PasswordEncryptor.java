package com.example.todo_app.dto.utility;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    public static String hashPassword(String enteredPassword){
        String generatedSalt = BCrypt.gensalt(10);
        return BCrypt.hashpw(enteredPassword,generatedSalt);
    }
}
