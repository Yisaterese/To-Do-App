package com.example.todo_app.service;

import com.example.todo_app.data.dto.request.*;
import com.example.todo_app.data.dto.request.DeleteUserRequest;
import com.example.todo_app.data.dto.request.viewAllTasByPriorityRequest;
import com.example.todo_app.data.dto.request.GetUserPendingTasksRequest;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.dto.response.DeleteUserResponse;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.TaskRepository;
import com.example.todo_app.data.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    RegisterUserRequest registerUserRequest = new RegisterUserRequest();
    GetTaskRequest getTaskRequest = new GetTaskRequest();
    LoginRequest loginRequest = new LoginRequest();
    LogOutRequest logOutRequest = new LogOutRequest();
    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    GetUserRequest getUserRequest = new GetUserRequest();
    static CreateTaskRequest createTaskRequest = new CreateTaskRequest();
    static RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
    AssignTaskRequest assignTaskRequest = new AssignTaskRequest();
    UpDateTaskRequest upDateTaskRequest = new UpDateTaskRequest();
    DeleteAllTaskByUserRequest deleteTaskByUserRequest = new DeleteAllTaskByUserRequest();
    viewAllTasByPriorityRequest getTaskByPriorityRequest = new viewAllTasByPriorityRequest();
    GetUserPendingTasksRequest getAllPendingTasksRequests = new GetUserPendingTasksRequest();
    CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
    User newUser = new User();
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();

    }
    @Test
    void registerUserTest() {
        registerUserRequest.setUserName("Dayo abiodum");
        registerUserRequest.setPassword("Password12#");
        registerUserRequest.setEmail("darhyor2050@gmail.com");
        newUser.setUserName(registerUserRequest.getUserName());
        newUser.setPassword(registerUserRequest.getPassword());
        newUser.setEmail(registerUserRequest.getEmail());
        userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
    }
    @Test
    void loginUserTest() {

        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());
    }
    @Test
    void logOutUserTest() {

        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        logOutRequest.setUserId(loginRequest.getUserId());
        userService.logOut(logOutRequest);
        User userToLogOut = userService.findUserById(logOutRequest.getUserId());
        Assertions.assertNotNull(userToLogOut);
        assertFalse(userToLogOut.isLoggedIn());
    }
    @Test
    void deleteUserByUserNameTest() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();

        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        User savedUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserName(userToLogin.getUserName());
        DeleteUserResponse deleteuserResponse =  userService.deleteUserByUserName(deleteUserRequest);
        Assertions.assertNotNull(deleteuserResponse);
        Assertions.assertEquals(0,userRepository.count());
    }
    private static RegisterUserRequest getRegisterUserRequest() {
        return getUserRequest("Dayo Abiodum", "darhyor2050@gmail.com");
    }
    @Test
    public void updateUserTest(){
        final RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("Joseph");
        registerUserRequest.setPassword("passworD#3");
        registerUserRequest.setEmail("josephyisa98@gmail.com");
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        updateUserRequest.setPasswordToBeUpdated(userToLogin.getPassword());
        updateUserRequest.setEmailToBeUpdated(userToLogin.getEmail());
        updateUserRequest.setUserNameTBeUpdated(userToLogin.getUserName());
        updateUserRequest.setUserId(registerUserResponse.getUserId());

        UpdateUserResponse updateUserResponse = userService.updateUserProfile(updateUserRequest);
        Assertions.assertNotNull(updateUserResponse);
    }
    @Test
    void createTask() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

    }
    @Test
    void getUserTasks() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        viewAllTasksByUserRequest viewAllTasksByUserrequest = new viewAllTasksByUserRequest();
        viewAllTasksByUserrequest.setUserId(registerUserResponse.getUserId());
        List<Task> userTasks = userService.getUserTasks(viewAllTasksByUserrequest);
        Assertions.assertNotNull(userTasks);
        Assertions.assertEquals(1,userTasks.size());
    }
    private static CreateTaskRequest getCreateTaskRequest(RegisterUserResponse registerUserResponse) {
        createTaskRequest.setTitle("Clean the house");
        createTaskRequest.setTaskPriority("High");
        createTaskRequest.setDescription("Do a thorough cleaning");
        createTaskRequest.setUserId(registerUserResponse.getUserId());
        return createTaskRequest;
    }
    @Test
    void assignTask() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
        User foundUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertEquals("dayo abiodum",foundUser.getUserName());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final RegisterUserRequest registerUserRequest2 = getUserRequest("Victor", "victor@gmail.com");
        RegisterUserResponse registerUserResponse2 = userService.registerUser(registerUserRequest2);
        Assertions.assertEquals(2,userRepository.count());
        User user = userService.findUserByUserName(registerUserRequest2.getUserName());
        Assertions.assertEquals("victor",user.getUserName());


        loginRequest.setUserId(registerUserResponse2.getUserId());
        loginRequest.setPassword(registerUserRequest2.getPassword());
        userService.login(loginRequest);

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);

        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUserWithTask = userService.findUserById(createTaskRequest.getUserId());
        Assertions.assertEquals(1,foundUserWithTask.getAllTasks().size());

        getTaskRequest.setUserId(foundUserWithTask.getId());
        getTaskRequest.setTitle(createTaskRequest.getTitle());

        Task foundUserTask = userService.findTaskByTitle(getTaskRequest);
        Assertions.assertEquals("clean the house",foundUserTask.getTitle());

        assignTaskRequest.setAssignerUserId(registerUserResponse.getUserId());
        assignTaskRequest.setAssigneeUserId(registerUserResponse2.getUserId());
        assignTaskRequest.setTitle(foundUserTask.getTitle());

        AssignTaskResponse assignTaskResponse = userService.assignTask(assignTaskRequest);
        User assigner = userService.findUserByUserName(foundUserWithTask.getUserName());
        User assignee = userService.findUserByUserName(user.getUserName());

        Assertions.assertNotNull(assignTaskResponse);
        Assertions.assertEquals(1,assignee.getAllTasks().size());
        Assertions.assertEquals(0,assigner.getAllTasks().size());

    }
    @Test
    void shareTask() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
        User foundUser = userService.findUserByUserName(registerUserRequest.getUserName());
        Assertions.assertEquals("dayo abiodum",foundUser.getUserName());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        System.out.println(userToLogin);
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final RegisterUserRequest registerUserRequest2 = getUserRequest("Victor", "teresejosephyisa@gmail.com");
        RegisterUserResponse registerUserResponse1 = userService.registerUser(registerUserRequest2);
        Assertions.assertEquals(2,userRepository.count());
        User user = userService.findUserByUserName(registerUserRequest2.getUserName());
        Assertions.assertEquals("victor",user.getUserName());

        loginRequest.setUserId(user.getId());
        loginRequest.setPassword(registerUserRequest2.getPassword());
        userService.login(loginRequest);

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);

        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUserWithTask = userService.findUserById(createTaskRequest.getUserId());
        Assertions.assertEquals(1,foundUserWithTask.getAllTasks().size());
        getTaskRequest.setUserId(foundUserWithTask.getId());
        getTaskRequest.setTitle(createTaskRequest.getTitle());

        Task foundUserTask = userService.findTaskByTitle(getTaskRequest);
        Assertions.assertEquals("clean the house",foundUserTask.getTitle());

        User assigner = userService.findUserByUserName(foundUserWithTask.getUserName());
        System.out.println(assigner.getUserName());
        User assignee = userService.findUserByUserName(user.getUserName());
        System.out.println(assignee.getUserName());

        Assertions.assertEquals(0,assignee.getAllTasks().size());
        Assertions.assertEquals(1,assigner.getAllTasks().size());

        ShareTaskRequest shareTaskRequest = new ShareTaskRequest();
        shareTaskRequest.setAssignerId(assigner.getId());
        shareTaskRequest.setAssigneeId(assignee.getId());
        shareTaskRequest.setTitle(createTaskRequest.getTitle());

        ShareTaskResponse shareTaskResponse = userService.shareTask(shareTaskRequest);

        assignee = userService.findUserByUserName(user.getUserName());
        Assertions.assertNotNull(shareTaskResponse);
        Assertions.assertEquals(1,assigner.getAllTasks().size());
        Assertions.assertEquals(1,assignee.getAllTasks().size());
    }
    private static RegisterUserRequest getUserRequest(String userName, String mail) {
        registerUserRequest2.setUserName(userName);
        registerUserRequest2.setPassword("P#assword12");
        registerUserRequest2.setEmail(mail);
        return registerUserRequest2;
    }
    @Test
    void getUserByEmail() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());
    }
    @Test
    void deleteAllTasksByUser() {
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse response = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());
        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(response.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(response);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());

        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        deleteTaskByUserRequest.setUserId(foundUser.getId());
        DeleteAllTaskResponse deleteAllTaskResponse = userService.deleteAllTasksByUser(deleteTaskByUserRequest);
        User existingUser = userService.findUserById(deleteTaskByUserRequest.getUserId());

        Assertions.assertNotNull(deleteAllTaskResponse);
        Assertions.assertEquals(0,existingUser.getAllTasks().size());
    }
    @Test
    void getAllTasksByUser() {

        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        System.out.println(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());

        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        System.out.println(taskRepository.findAll());
        viewAllTasksByUserRequest viewAllTasksByUserrequest = new viewAllTasksByUserRequest();
        viewAllTasksByUserrequest.setUserId(registerUserResponse.getUserId());
        List<Task> userTasks = userService.getAllTaskSByUser(viewAllTasksByUserrequest);
        Assertions.assertNotNull(userTasks);
        assertFalse(userTasks.isEmpty());

    }
    @Test
    void findUserTaskByPriority(){
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        System.out.println(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());

        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        getTaskByPriorityRequest.setTaskPriority(createTaskRequest.getTaskPriority());
        getTaskByPriorityRequest.setUserId(registerUserResponse.getUserId());

        List<Task> priorityTaskByUser =  userService.findUserTasksByPriority(getTaskByPriorityRequest);
        Assertions.assertNotNull(priorityTaskByUser);
        assertFalse(priorityTaskByUser.isEmpty());

    }
    @Test
    void getPendingTasksTest(){
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());
        Assertions.assertEquals(1,foundUser.getPendingTasks().size());

        getAllPendingTasksRequests.setUserId(foundUser.getId());

        List<Task> listOfUserPendingTasks = userService.viewAllPendingTasksByUser(getAllPendingTasksRequests);
        Assertions.assertFalse(listOfUserPendingTasks.isEmpty());
        Assertions.assertEquals("clean the house",listOfUserPendingTasks.getFirst().getTitle());
    }
    @Test
    void completeTask(){
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());
        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());
        Assertions.assertEquals(1,foundUser.getPendingTasks().size());

        completeTaskRequest.setUserId(registerUserResponse.getUserId());
        completeTaskRequest.setTitle(createTaskRequest.getTitle());


       CompleteTaskResponse listOfUserPendingTasks = userService.completeTask(completeTaskRequest);
         foundUser = userService.findUserById(createTaskRequest.getUserId());
        Assertions.assertNotNull(listOfUserPendingTasks);
        Assertions.assertTrue(foundUser.getAllTasks().isEmpty());
    }

    @Test
    public  void updateTask(){
        final RegisterUserRequest registerUserRequest = getRegisterUserRequest();
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);

        GetUserRequest getUserRequest = new GetUserRequest();
        getUserRequest.setEmail(registerUserRequest.getEmail());

        User savedUser = userService.getUserByEmail(getUserRequest);
        Assertions.assertNotNull(savedUser);
        System.out.println(savedUser);
        Assertions.assertEquals(1,userRepository.count());

        loginRequest.setUserId(registerUserResponse.getUserId());
        loginRequest.setPassword(registerUserRequest.getPassword());
        userService.login(loginRequest);

        User userToLogin = userService.findUserById(loginRequest.getUserId());
        Assertions.assertNotNull(userToLogin);
        Assertions.assertTrue(userToLogin.isLoggedIn());

        final CreateTaskRequest createTaskRequest = getCreateTaskRequest(registerUserResponse);
        CreateTaskResponse createTaskResponse = userService.createTask(createTaskRequest);
        Assertions.assertNotNull(createTaskResponse);
        User foundUser = userService.findUserById(createTaskRequest.getUserId());

        System.out.println(foundUser);
        Assertions.assertEquals(1,foundUser.getAllTasks().size());

        getTaskRequest.setUserId(foundUser.getId());
        getTaskRequest.setTitle("clean the house");
        Task foundTask = userService.findTaskByTitle(getTaskRequest);

        upDateTaskRequest.setUserId(foundUser.getId());
        upDateTaskRequest.setTaskPriority("'Medium'");
        upDateTaskRequest.setDueDate("2024-05-16");
        upDateTaskRequest.setDescription("Jump higher");
        upDateTaskRequest.setTaskToBeUpdatedTitle("clean the house");
        upDateTaskRequest.setTitle("Jump now");

        UpDateTaskResponse upDateTaskResponse = userService.updateTask(upDateTaskRequest);
        Assertions.assertNotNull(upDateTaskResponse);
        getTaskRequest.setTitle("Jump now");
       Task  foundTask1 = userService.findTaskByTitle(getTaskRequest);

        Assertions.assertEquals("jump now",foundTask1.getTitle());



    }
}