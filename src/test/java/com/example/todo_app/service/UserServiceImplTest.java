package com.example.todo_app.service;

import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.TaskRepository;
import com.example.todo_app.data.repository.UserRepository;
import com.example.todo_app.dto.request.*;
import com.example.todo_app.dto.response.*;
import com.example.todo_app.dto.utility.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }
    @Test
    void registerUserTest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        User newUser = new User();
        Address address = new Address();
        registerUserRequest.setUserName("Ada Mne");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("adamne@gmail.com");
        registerUserRequest.setPhoneNumber("234523456");
        address.setCityName("Lagos");
        address.setState("Lagos");
        address.setCountry("Nigeria");
        address.setHouseNumber("34A");
        address.setStreetName("Halbert Macauley");
        registerUserRequest.setUserAddress(address);
        newUser.setUserName(registerUserRequest.getUserName());
        newUser.setPassword(registerUserRequest.getPassword());
        newUser.setPhoneNumber(registerUserRequest.getPhoneNumber());
        newUser.setEmail(registerUserRequest.getEmail());
        newUser.setUserAddress(registerUserRequest.getUserAddress());
        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
    }
    @Test
    void loginUserTest() {

        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());
    }
    @Test
    void logOutUserTest() {

        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());


        LogOutRequest logOutRequest = new LogOutRequest();
        logOutRequest.setUserName(loginRequest.getUserName());
        userService.logOut(logOutRequest);
        User userToLogOut = userService.findUserByUserName(logOutRequest.getUserName());
        Assertions.assertNotNull(userToLogOut);
        Assertions.assertFalse(userToLogOut.isLoggedIn());
    }
    @Test
    void deleteUserByUserNameTest() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserName(userToLogin.getUserName());
        DeleteUserResponse deleteuserResponse =  userService.deleteUserByUserName(deleteUserRequest);
        Assertions.assertNotNull(deleteuserResponse);
        Assertions.assertEquals(0,userRepository.count());
    }
    private static RegisterUserRequest getRegisterUserRequest() {
        return getUserRequest("Joseph", "Joseph@gmail.com");
    }
    @Test
    public void updateUserTest(){
        final RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        Address address = new Address();
        registerUserRequest.setUserName("Joseph");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("Joseph@gmail.com");
        registerUserRequest.setPhoneNumber("234523456");
        address.setCityName("Lagos");
        address.setState("Lagos");
        address.setCountry("Nigeria");
        address.setHouseNumber("34A");
        address.setStreetName("Halberd Macaulay");
        registerUserRequest.setUserAddress(address);
        userService.registerUser(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());


        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setPassword(userToLogin.getPassword());
        updateUserRequest.setDateOFBirth(userToLogin.getDateOFBirth());
        updateUserRequest.setEmail(userToLogin.getEmail());
        updateUserRequest.setUserAddress(userToLogin.getUserAddress());
        updateUserRequest.setPhoneNumber(userToLogin.getPhoneNumber());
        updateUserRequest.setUserName(userToLogin.getUserName());

        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);
        Assertions.assertNotNull(updateUserResponse);
    }
    @Test
    void createTask() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(savedUser);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserByUserName(createTaskRequest.getUserName());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

    }
    @Test
    void displayAllTasks(){
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(savedUser);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserByUserName(createTaskRequest.getUserName());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        List<Task> userAllTasks = userService.displayAllTasks();
        Assertions.assertNotNull(userAllTasks);
        Assertions.assertFalse(userAllTasks.isEmpty());
    }
    @Test
    void deleteAllTasks() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(savedUser);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserByUserName(createTaskRequest.getUserName());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        List<Task> userAllTasks = userService.displayAllTasks();
        Assertions.assertNotNull(userAllTasks);
        Assertions.assertFalse(userAllTasks.isEmpty());
        DeleteAllTaskResponse    deleteAllTaskResponse  = userService.deleteAllTasks();
        Assertions.assertNotNull(deleteAllTaskResponse);
        List<Task> foundTasks = userService.displayAllTasks();
        Assertions.assertTrue(foundTasks.isEmpty());

    }
    @Test
    void getUserTasks() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(savedUser);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserByUserName(createTaskRequest.getUserName());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        GetAllTasksByUserRequest getAllTasksByUserRequest = new GetAllTasksByUserRequest();
        getAllTasksByUserRequest.setUserName(foundUser.getUserName());
        List<Task> userTasks = userService.getUserTasks(getAllTasksByUserRequest);
        Assertions.assertNotNull(userTasks);
        Assertions.assertEquals(1,userTasks.size());
    }
    @Test
    void assignTask() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        System.out.println(savedUser);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
        User foundUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertEquals("Joseph",foundUser.getUserName());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getEmail());
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserByUserName(loginRequest.getUserName());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final RegisterUserRequest registerUserRequest2 = getUserRequest("Victor", "victor@gmail.com");
        userService.registerUser(registerUserRequest2);
        Assertions.assertEquals(2,userRepository.count());
        User user = userService.findUserByUserName(registerUserRequest2.getUserName());
        Assertions.assertEquals("Victor",user.getUserName());

        loginRequest.setUserName(registerUserRequest2.getEmail());
        loginRequest.setUserName(registerUserRequest2.getUserName());
        loginRequest.setPassword(registerUserRequest2.getPassword());
        userService.login(loginRequest);

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(foundUser);

        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUserWithTask = userService.findUserByUserName(createTaskRequest.getUserName());
//        System.out.println(foundUserWithTask);
        Assertions.assertEquals(1,foundUserWithTask.getAllTasks().size());



        Task foundUserTask = userService.findTaskByTitle(createTaskRequest);
        Assertions.assertEquals("Clean the house",foundUserTask.getTitle());

        AssignTaskRequest assignTaskRequest = new AssignTaskRequest();
        assignTaskRequest.setAssignerUserName(foundUserWithTask.getUserName());
        assignTaskRequest.setAssigneeUserName(user.getUserName());
        assignTaskRequest.setTitle(foundUserTask.getTitle());

        AssignTaskResponse assignTaskResponse = userService.assignTask(assignTaskRequest);
        User assigner = userService.findUserByUserName(foundUserWithTask.getUserName());
        User assignee = userService.findUserByUserName(user.getUserName());
//        System.out.println(assignee);
        Assertions.assertNotNull(assignTaskResponse);
        Assertions.assertEquals(1,assignee.getAllTasks().size());
        Assertions.assertEquals(0,assigner.getAllTasks().size());

    }
    private static CreateTaskRequest getCreateTaskRequest(User foundUser) {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Clean the house");
        createTaskRequest.setTaskPriority("High");
        createTaskRequest.setDescription("Do a thorough cleaning");
        createTaskRequest.setTaskUniqueNumber("233445");
        createTaskRequest.setUserName(foundUser.getUserName());
        return createTaskRequest;
    }
    private static RegisterUserRequest getUserRequest(String Victor, String mail) {
        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        Address address = new Address();
        registerUserRequest2.setUserName(Victor);
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setEmail(mail);
        registerUserRequest2.setPhoneNumber("234523456");
        address.setCityName("Lagos");
        address.setState("Lagos");
        address.setCountry("Nigeria");
        address.setHouseNumber("34A");
        address.setStreetName("Halberd Macaulay");
        registerUserRequest2.setUserAddress(address);
        return registerUserRequest2;
    }
    @Test
    void getUserByEmail() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
    }
    @Test
    void getAllUsers() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
        List<User> allUsers = userService.getAllUsers();
        Assertions.assertNotNull(allUsers);
    }
    @Test
    void deleteAllUsers() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
        DeleteAllUserResponse deleteAllUserResponse = userService.deleteAllUsers();
        Assertions.assertNotNull(deleteAllUserResponse);
        Assertions.assertEquals(0,userRepository.count());

    }
}