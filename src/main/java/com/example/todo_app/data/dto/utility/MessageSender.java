package com.example.todo_app.data.dto.utility;

import com.example.todo_app.data.dto.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageSender {
    @Autowired
     JavaMailSender javaMailSender;

  public  void registrationMessage(RegisterUserRequest userRequest){
      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      String senderMailAddress = "teresejosephyisa@gmail.com";
      simpleMailMessage.setFrom(senderMailAddress);
      simpleMailMessage.setTo(userRequest.getEmail());
      simpleMailMessage.setSubject(Message.registrationMessageSubject());
      simpleMailMessage.setText(Message.registrationMessageBody(userRequest.getUserName()));
      javaMailSender.send(simpleMailMessage);

  }

  public static boolean isValidEmail(String userEmail){
      String emailPattern = "^(?!do)(?!.*\\.\\.)(?!.*\\.\\.)(?!.*\\.\\.)(?=.*[a-z])[a-z]+\\.[a-z]+@([a-z]+\\.)?com$";
      Pattern pattern = Pattern.compile(emailPattern);
      Matcher matcher = pattern.matcher(userEmail);
      return matcher.matches();

  }



}
