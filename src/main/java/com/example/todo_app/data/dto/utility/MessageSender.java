package com.example.todo_app.data.dto.utility;

import com.example.todo_app.data.dto.request.RegisterUserRequest;
import com.example.todo_app.data.dto.utility.exception.ToDoRunTimeException;
import com.example.todo_app.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageSender {
    @Autowired
     JavaMailSender javaMailSender;
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    static String senderMailAddress = "teresejosephyisa@gmail.com";

  public  void registrationMessage(RegisterUserRequest userRequest){
      try {
          simpleMailMessage.setFrom(senderMailAddress);
          simpleMailMessage.setTo(userRequest.getEmail());
          simpleMailMessage.setSubject(Message.registrationMessageSubject());
          simpleMailMessage.setText(Message.registrationMessageBody(userRequest.getUserName()));
          javaMailSender.send(simpleMailMessage);
      }catch (MailSendException exception){
          throw new ToDoRunTimeException("Invalid email address");
      }

  }
    public  void shareTaskMessage(User user, String title){
      try {
          simpleMailMessage.setFrom(senderMailAddress);
          simpleMailMessage.setTo(user.getEmail());
          simpleMailMessage.setSubject(Message.shareTaskSubjectMessage());
          simpleMailMessage.setText(Message.shareTaskMessage(title, user.getUserName()));
          javaMailSender.send(simpleMailMessage);
      }catch (MailSendException e){
          throw new ToDoRunTimeException("Invalid email address");
      }
    }

    public  void assignTaskMessage(User user, String title){
      try {
          simpleMailMessage.setFrom(senderMailAddress);
          simpleMailMessage.setTo(user.getEmail());
          simpleMailMessage.setSubject(Message.assigneTaskSubject());
          simpleMailMessage.setText(Message.assignTaskMessage(user.getUserName(), title));
          javaMailSender.send(simpleMailMessage);
      }catch (MailSendException exception){
          throw new ToDoRunTimeException("Invalid email address");
      }

    }


    public static boolean isValidEmail(String userEmail){
      String emailPattern = "^(?!do)(?!.*\\.\\.)(?!.*\\.\\.)(?!.*\\.\\.)(?=.*[a-z])[a-z]+\\.[a-z]+@([a-z]+\\.)?com$";
      Pattern pattern = Pattern.compile(emailPattern);
      Matcher matcher = pattern.matcher(userEmail);
      return matcher.matches();

  }



}
