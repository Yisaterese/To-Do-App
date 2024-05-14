package com.example.todo_app.data.dto.utility;

import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    public static String hashPassword(String enteredPassword){
        String generatedSalt = BCrypt.gensalt(10);
        return BCrypt.hashpw(enteredPassword,generatedSalt);
    }

    public static void encodePassword(String enteredPassword, String savedPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean unHashedPasswordMatches = passwordEncoder.matches(enteredPassword,savedPassword);
        if(!unHashedPasswordMatches) throw new ToDoRunTimeException("Invalid login request password");
    }
}
