package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.UserRepository;
import com.example.todo_app.dto.request.*;
import com.example.todo_app.dto.response.*;
import com.example.todo_app.dto.utility.EmailValidator;
import com.example.todo_app.dto.utility.PasswordEncryptor;
import com.example.todo_app.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.todo_app.dto.utility.Mapper.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest){
            if(userRepository.existsByEmail(createTaskRequest.getEmail()))throw new ExistingUserException("email taken");
            User newUser = new User();
            setPropertiesForUser(createTaskRequest, newUser);
            userRepository.save(newUser);
            return mapRegisterResponse(newUser);
    }


    private static void setPropertiesForUser(RegisterUserRequest createTaskRequest, User newuser) {
        String hashedPassword = PasswordEncryptor.hashPassword(createTaskRequest.getPassword());
        String validEmail = EmailValidator.validateEmail(createTaskRequest.getEmail());

        newuser.setUserName(createTaskRequest.getUserName());
        newuser.setPassword(hashedPassword);
        newuser.setEmail(validEmail);
        newuser.setDateOFBirth(LocalDate.now());
        newuser.setUserAddress(createTaskRequest.getUserAddress());
    }


    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        User foundUser = userRepository.findByUserName(createTaskRequest.getUserName());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        foundUser.getAllTasks().forEach(task -> {if(task.getTitle().equals(createTaskRequest.getTitle()))throw new AlreadyExistingTaskException("Task with "+createTaskRequest.getTitle()+" already created");});
        if(!foundUser.isLogIn())throw new UserNotLoggedInException("User not logged in");
        Task createdTask = taskService.createTask(createTaskRequest);
        List<Task> tasks = foundUser.getAllTasks();
        tasks.add(createdTask);
        foundUser.setAllTasks(tasks);
        userRepository.save(foundUser);
        return mapCreateTaskResponse(createdTask);
    }

    @Override
    public List<Task> displayAllTasks(){
        return taskService.findAllTasks();
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasks(){
        return taskService.deleteAllTasks();

    }


    @Override
    public List<Task> getUserTasks(GetAllTasksByUserRequest getUserRequest) {
        User foundUser = userRepository.findByUserName(getUserRequest.getUserName());
        List<Task>  tasks = foundUser.getAllTasks();
        if (tasks.isEmpty())throw new TaskNotFoundException("No task found");
        return  tasks;//taskService.findAllTaskByUser(getUserRequest);
    }

    @Override
    public DeleteTaskResponse deleteTaskByUserName(DeleteTaskRequest deleteTaskRequest) {
        return taskService.deleteTaskByUserName(deleteTaskRequest);
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        User existingUser = userRepository.findByUserName(loginRequest.getEmail());
        //validateUserByEmail(loginRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        setUserProperties(loginRequest, existingUser);
        userRepository.save(existingUser);
        return  mapLoginResponse(existingUser);
    }

    private  void setUserProperties(LoginRequest loginRequest, User existingUser) {
        existingUser.setEmail(loginRequest.getEmail());
        existingUser.setUserName(loginRequest.getUserName());
        existingUser.setPassword(loginRequest.getPassword());
        existingUser.setLogIn(true);
    }

    private static void validateAlreadyLoggedIn(User existingUser) {
        if(existingUser.isLogIn())throw new UserAlreadyLoggedException("User already logged in ");
    }

    private static void validateUserByEmail(LoginRequest loginRequest, User existingUser) {
        if(!existingUser.getEmail().equals(loginRequest.getEmail()))throw new UserNotFoundException("user not found");
    }

    private static void validateIfIsNull(User existingUser) {
        if(existingUser == null)throw new UserNotFoundException("User not found");
    }

   @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest){
        User existingUser = getUserByEmail(logOutRequest);
        validateUserEmail(logOutRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedOut(existingUser);
        existingUser.setLogIn(false);
        userRepository.save(existingUser);
        return mapLogOutResponse(existingUser);

    }

    private void validateAlreadyLoggedOut(User existingUser) {
        if(!existingUser.isLogIn())throw new UserAlreadyLoggedOutException("User already logged out ");
    }

    private User getUserByEmail(LogOutRequest logOutRequest) {
        return userRepository.findByUserName(logOutRequest.getEmail());
    }

    private static void validateUserEmail(LogOutRequest logOutRequest, User existingUser) {
        if(!existingUser.getEmail().equals(logOutRequest.getEmail()))throw new UserNotFoundException("user not found");
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        User assignee = userRepository.findByUserName(assignTaskRequest.getAssigneeEmail());
        User assigner = userRepository.findByUserName(assignTaskRequest.getAssignerEmail());
        Task assignerTask_toBeAssigned = assigner.getAllTasks().stream().filter(task -> task.getTaskUniqueNumber().equals(assignTaskRequest.getTaskToBeAssigned())).findFirst().orElseThrow(() -> new TaskNotFoundException("Task not found"));
        assignee.getAllTasks().add(assignerTask_toBeAssigned);
        assignee.getAllTasks().remove(assignerTask_toBeAssigned);
        return mapAssignTaskResponse(assigner, assignee);
    }

    @Override
    public User getUserByEmail(GetUserRequest getUserRequest){
     User founduser = userRepository.findByUserName(getUserRequest.getEmail());
     if(founduser == null)throw new UserNotFoundException("User not found");
     return founduser;
    }

    @Override
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())throw  new UserNotFoundException("User not found");
        if(allUsers == null)throw  new UserNotFoundException("User not found");
        return allUsers;
    }

    @Override
    public DeleteAllUserResponse deleteAllUsers() {
        List<User> foundUsers = getAllUsers();
        System.out.println(foundUsers);
        if(!foundUsers.isEmpty())throw new UserNotFoundException("No User found");
        userRepository.deleteAll();
        return mapDeleteAllUsersResponse();
    }





}
