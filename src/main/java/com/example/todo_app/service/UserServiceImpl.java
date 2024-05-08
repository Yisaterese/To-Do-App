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
            userRepository.findAll().forEach(user ->{if(user.getEmail().equals(createTaskRequest.getEmail()))throw new ExistingUserException("email taken");});
            User newUser = new User();
            setPropertiesForUser(createTaskRequest, newUser);
            userRepository.save(newUser);
            return mapRegisterResponse(newUser);
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        User existingUser = userRepository.findUserByUserName(loginRequest.getUserName());
        //validateUserByEmail(loginRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        setUserProperties(loginRequest, existingUser);
        userRepository.save(existingUser);
        return  mapLoginResponse(existingUser);
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
        User foundUser = userRepository.findUserByUserName(createTaskRequest.getUserName());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        foundUser.getAllTasks().forEach(task -> {if(task.getTitle().equals(createTaskRequest.getTitle()))throw new AlreadyExistingTaskException("Task with "+createTaskRequest.getTitle()+" already created");});
        if(!foundUser.isLoggedIn())throw new UserNotLoggedInException("User not logged in");
        Task createdTask = taskService.createTask(createTaskRequest);
        List<Task> tasks = foundUser.getAllTasks();
        tasks.add(createdTask);
        foundUser.setAllTasks(tasks);
        foundUser.setPendingTasks(tasks);
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
        User foundUser = userRepository.findUserByUserName(getUserRequest.getUserName());
        List<Task>  tasks = foundUser.getAllTasks();
        if (tasks.isEmpty())throw new TaskNotFoundException("No task found");
        return  tasks;//taskService.findAllTaskByUser(getUserRequest);
    }

    @Override
    public DeleteTaskResponse deleteTaskByUserName(DeleteTaskRequest deleteTaskRequest) {
        return taskService.deleteTaskByUserName(deleteTaskRequest);
    }

    private  void setUserProperties(LoginRequest loginRequest, User existingUser) {
        PasswordEncryptor.encodePassword(loginRequest.getPassword(),existingUser.getPassword());
        if (!existingUser.getUserName().equals(loginRequest.getUserName()))throw new InvalidLoginRequest("Invalid login request username");
        existingUser.setLoggedIn(true);

    }

    private static void validateAlreadyLoggedIn(User existingUser) {
        if(existingUser.isLoggedIn())throw new UserAlreadyLoggedException("User already logged in ");
    }

    private static void validateUserByEmail(LoginRequest loginRequest, User existingUser) {
        if(!existingUser.getEmail().equals(loginRequest.getUserName()))throw new UserNotFoundException("user not found");
    }

    private static void validateIfIsNull(User existingUser) {
        if(existingUser == null)throw new UserNotFoundException("User not found");
    }

   @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest){
        User existingUser = userRepository.findUserByUserName(logOutRequest.getUserName());
        validateUser(logOutRequest, existingUser);
        validateIfIsNull(existingUser);
        validateAlreadyLoggedOut(existingUser);
        existingUser.setLoggedIn(false);
        userRepository.save(existingUser);
        return mapLogOutResponse(existingUser);

    }

    private void validateAlreadyLoggedOut(User existingUser) {
        if(!existingUser.isLoggedIn())throw new UserAlreadyLoggedOutException("User already logged out ");
    }

    private User getUserByEmail(LogOutRequest logOutRequest) {
        return userRepository.findUserByUserName(logOutRequest.getUserName());
    }

    private static void validateUser(LogOutRequest logOutRequest, User existingUser) {
        if(!existingUser.getUserName().equals(logOutRequest.getUserName()))throw new UserNotFoundException("user not found");
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        User assignee = findUserByUserName(assignTaskRequest.getAssigneeUserName());
        User assigner = findUserByUserName(assignTaskRequest.getAssignerUserName());
        validateIfIsNull(assignee);
        validateIfIsNull(assigner);
        if(!assigner.isLoggedIn())throw new UserNotLoggedInException("User not logged in");
        Task assignerTask_toBeAssigned = assigner.getAllTasks().stream().filter(task ->
                task.getTitle().equals(assignTaskRequest.getTitle())).findFirst().orElseThrow(() -> new TaskNotFoundException("Task not found"));

        List<Task> assigneeTasks = assignee.getAllTasks();
        assigneeTasks.add(assignerTask_toBeAssigned);
        assignee.setAllTasks(assigneeTasks);
        userRepository.save(assignee);
        List<Task> assignerTasks = assigner.getAllTasks();
        assignerTasks.remove(assignerTask_toBeAssigned);
        userRepository.save(assigner);

        return mapAssignTaskResponse(assigner, assignee);
    }

    @Override
    public User getUserByEmail(GetUserRequest getUserRequest){
     User founduser = userRepository.findByEmail(getUserRequest.getEmail());
     if(founduser == null)throw new UserNotFoundException("User not found");
     return founduser;
    }


    @Override
    public DeleteAllUserResponse deleteAllUsers() {
        List<User> foundUsers = getAllUsers();
        if(foundUsers.isEmpty())throw new UserNotFoundException("No User found");
        userRepository.deleteAll();
        return mapDeleteAllUsersResponse();
    }


    @Override
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())throw  new UserNotFoundException("No user found");
        return allUsers;
    }

    @Override
    public User  findUserByUserName(String userName) {
        User foundUser = userRepository.findUserByUserName(userName);
        if(foundUser == null)throw new UserNotFoundException("User not found");
        return foundUser;
    }
    @Override
    public DeleteUserResponse deleteUserByUserName(DeleteUserRequest deleteUserRequest) {
        User foundUser = findUserByUserName(deleteUserRequest.getUserName());
        validateIfIsNull(foundUser);
        userRepository.deleteByUserName(deleteUserRequest.getUserName());
        return  mapDeleteUserResponse();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User foundUser = findUserByUserName(updateUserRequest.getUserName());
        if(!foundUser.isLoggedIn())throw new UserNotLoggedInException("User not logged in");
        foundUser.setUserName(updateUserRequest.getUserName());
        foundUser.setPassword(updateUserRequest.getPassword());
        foundUser.setEmail(updateUserRequest.getEmail());
        foundUser.setUserAddress(updateUserRequest.getUserAddress());
        foundUser.setPhoneNumber(updateUserRequest.getPhoneNumber());
        foundUser.setDateOFBirth(updateUserRequest.getDateOFBirth());
        userRepository.delete(foundUser);
        userRepository.save(foundUser);
        return mapUpdateUserResponse(foundUser);
    }

    @Override
    public Task findTaskByTitle(CreateTaskRequest createTaskRequest) {
        return taskService.findTaskByUser(createTaskRequest.getUserName());
    }


}
