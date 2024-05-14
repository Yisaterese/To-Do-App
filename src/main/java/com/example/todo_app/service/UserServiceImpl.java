package com.example.todo_app.service;

import com.example.todo_app.data.dto.request.*;
import com.example.todo_app.data.dto.response.*;
import com.example.todo_app.data.dto.utility.MessageSender;
import com.example.todo_app.data.dto.utility.PasswordEncryptor;
import com.example.todo_app.data.dto.utility.exception.*;
import com.example.todo_app.data.model.Task;
import com.example.todo_app.data.model.User;
import com.example.todo_app.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.todo_app.data.dto.utility.Mapper.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    MessageSender messageSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest createTaskRequest){
        userRepository.findAll().forEach(user ->{if(user.getEmail().equals(createTaskRequest.getEmail()))throw new ToDoRunTimeException("email taken");});
        final User newUser = getUserCreated(createTaskRequest);
        return mapRegisterResponse(newUser);
    }

    private User getUserCreated(RegisterUserRequest createTaskRequest) {
        User newUser = new User();
        setPropertiesForUser(createTaskRequest, newUser);
        userRepository.save(newUser);
        return newUser;
    }

    private  void setPropertiesForUser(RegisterUserRequest createTaskRequest, User newuser) {
        String hashedPassword = PasswordEncryptor.hashPassword(createTaskRequest.getPassword());
        createNewUser(createTaskRequest, newuser, hashedPassword, createTaskRequest.getEmail());
    }

    private  void createNewUser(RegisterUserRequest registerUserRequest, User newuser, String hashedPassword, String validEmail) {
        newuser.setUserName(registerUserRequest.getUserName().toLowerCase().trim());
        newuser.setPassword(hashedPassword);
        newuser.setEmail(validEmail);
        messageSender.registrationMessage(registerUserRequest);
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        User existingUser = userRepository.findUserById(loginRequest.getUserId());
        validateUser(loginRequest, existingUser);
        userRepository.save(existingUser);
        return  mapLoginResponse(existingUser);
    }

    private void validateUser(LoginRequest loginRequest, User existingUser) {
        validateIfUserIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        setUserProperties(loginRequest, existingUser);
    }





    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        final User foundUser = getUser(createTaskRequest);
        foundUser.getAllTasks().forEach(task -> {if(task.getTitle().equals(createTaskRequest.getTitle().trim()))throw new ToDoRunTimeException("Task with "+createTaskRequest.getTitle()+" already created");});
        ifUserLoggedIn(foundUser);
        final Task createdTask = getCreatedTask(createTaskRequest, foundUser);
        return mapCreateTaskResponse(createdTask);
    }

    private Task getCreatedTask(CreateTaskRequest createTaskRequest, User foundUser) {
        Task createdTask = taskService.createTask(createTaskRequest);
        List<Task> tasks = foundUser.getAllTasks();
        addTaskCreatedToUser(foundUser, tasks, createdTask);
        return createdTask;
    }

    private void addTaskCreatedToUser(User foundUser, List<Task> tasks, Task createdTask) {
        tasks.add(createdTask);
        foundUser.setAllTasks(tasks);
        foundUser.setPendingTasks(tasks);
        userRepository.save(foundUser);
    }

    private User getUser(CreateTaskRequest createTaskRequest) {
        User foundUser = userRepository.findUserById(createTaskRequest.getUserId());
        if (foundUser == null) throw new ToDoRunTimeException("User not found");
        return foundUser;
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
    public List<Task> getUserTasks(viewAllTasksByUserRequest getUserRequest) {
        User foundUser = findUserById(getUserRequest.getUserId());
        validateIfUserIsNull(foundUser);
        List<Task>  tasks = foundUser.getAllTasks();
        if (tasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return  tasks;
    }

    @Override
    public DeleteTaskResponse deleteTaskByUser(DeleteTaskRequest deleteTaskRequest) {
        User existingUser = userRepository.findUserById(deleteTaskRequest.getUserId());
        validateIfUserIsNull(existingUser);
        removeTask(deleteTaskRequest, existingUser);
        return taskService.deleteTaskByUser(deleteTaskRequest);
    }

    private void removeTask(DeleteTaskRequest deleteTaskRequest, User existingUser) {
        Task task = getTask(existingUser, deleteTaskRequest.getUserId());
        existingUser.getAllTasks().remove(task);
        userRepository.save(existingUser);
    }

    private  void setUserProperties(LoginRequest loginRequest, User existingUser) {
        PasswordEncryptor.encodePassword(loginRequest.getPassword(),existingUser.getPassword());
        if (!existingUser.getId().equals(loginRequest.getUserId()))throw new ToDoRunTimeException("Invalid login request");
        existingUser.setLoggedIn(true);
    }

    private static void validateAlreadyLoggedIn(User existingUser) {
        if(existingUser.isLoggedIn())throw new ToDoRunTimeException("User already logged in ");
    }

    private static void validateUserByEmail(LoginRequest loginRequest, User existingUser) {
        if(!existingUser.getId().equals(loginRequest.getUserId()))throw new ToDoRunTimeException("user not found");
    }



   @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest){
       final User existingUser = isValidUser(logOutRequest);
       existingUser.setLoggedIn(false);
        userRepository.save(existingUser);
        return mapLogOutResponse(existingUser);
    }

    private User isValidUser(LogOutRequest logOutRequest) {
        User existingUser = userRepository.findUserById(logOutRequest.getUserId());
        isValidUserToLogOut(logOutRequest, existingUser);
        return existingUser;
    }

    private void isValidUserToLogOut(LogOutRequest logOutRequest, User existingUser) {
        validateUser(logOutRequest, existingUser);
        validateIfUserIsNull(existingUser);
        validateAlreadyLoggedOut(existingUser);
    }

    private static void validateIfUserIsNull(User existingUser) {
        if(existingUser == null)throw new ToDoRunTimeException("User not found");
    }

    private void validateAlreadyLoggedOut(User existingUser) {
        if(!existingUser.isLoggedIn())throw new ToDoRunTimeException("User already logged out ");
    }

    private User getUserByEmail(LogOutRequest logOutRequest) {
        return userRepository.findUserByUserName(logOutRequest.getUserId());
    }

    private static void validateUser(LogOutRequest logOutRequest, User existingUser) {
        if(!existingUser.getId().equals(logOutRequest.getUserId()))throw new ToDoRunTimeException("user not found");
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        User assignee = findUserById(assignTaskRequest.getAssigneeUserId());
        User assigner = findUserById(assignTaskRequest.getAssignerUserId());
        validateUser(assignee, assigner);
        assignTaskToAssignee(assignee,  assignTaskRequest,assigner);
        return mapAssignTaskResponse(assigner, assignee);
    }

    private void assignTaskToAssignee(User assignee,AssignTaskRequest assignTaskRequest, User assigner) {
        final Task assignerTask_toBeAssigned = getTask(assigner, assignTaskRequest.getTitle().toLowerCase().trim());
        final List<Task> assigneeTasks = addTaskToBeAssigned_toAssignee(assignee, assignerTask_toBeAssigned);
        assignee.setAllTasks(assigneeTasks);
        userRepository.save(assignee);
        deleteAssignedTaskFromAssigner(assignerTask_toBeAssigned, assigner);
    }

    private static Task getTask(User assigner, String title) {
        return assigner.getAllTasks().stream().filter(task -> task.getTitle().equals(title.toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));
    }

    private static List<Task> addTaskToBeAssigned_toAssignee(User assignee, Task assignerTask_toBeAssigned) {
        List<Task> assigneeTasks = assignee.getAllTasks();
        assigneeTasks.add(assignerTask_toBeAssigned);
        return assigneeTasks;
    }

    private void deleteAssignedTaskFromAssigner(Task assignerTask_toBeAssigned, User assigner) {
        List<Task> assignerTasks = assigner.getAllTasks();
        assignerTasks.remove(assignerTask_toBeAssigned);
        userRepository.save(assigner);
    }

    private static void validateUser(User assignee, User assigner) {
        validateIfUserIsNull(assignee);
        validateIfUserIsNull(assigner);
        ifUserLoggedIn(assigner);
    }

    @Override
    public ShareTaskResponse shareTask(ShareTaskRequest shareTaskRequest) {
        User assignee = findUserById(shareTaskRequest.getAssigneeId());
        User assigner = findUserById(shareTaskRequest.getAssignerId());
        validateUser(assignee, assigner);
        shareTask(shareTaskRequest, assigner, assignee);
        return mapShareTaskResponse();
    }

    private void shareTask(ShareTaskRequest shareTaskRequest, User assigner, User assignee) {
        final Task assignerTask_toBeShared = getTask(assigner, shareTaskRequest.getTitle());
        List<Task> assigneeTasks = assignee.getAllTasks();
        addTaskToAssignee(assignee, assigneeTasks, assignerTask_toBeShared);
    }

    private void addTaskToAssignee(User assignee, List<Task> assigneeTasks, Task assignerTask_toBeShared) {
        assigneeTasks.add(assignerTask_toBeShared);
        assignee.setAllTasks(assigneeTasks);
        assignee.getPendingTasks().add(assignerTask_toBeShared);
        userRepository.save(assignee);
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasksByUser(DeleteAllTaskByUserRequest deleteTaskByUserRequest) {
        User foundUser =    findUserById(deleteTaskByUserRequest.getUserId());
        deleteAllUserTasks(foundUser);
        clearUserTasks(foundUser);
        return mapDeleteTasksResponse();
    }

    private void clearUserTasks(User foundUser) {
        taskService.findAllTasks().forEach(task -> {if(task.getUserId().equals(foundUser.getId()))taskService.deleteAllTasks();});
        List<Task> allTasks = validateTasks(foundUser);
        allTasks.clear();
        userRepository.save(foundUser);
    }

    private void deleteAllUserTasks(User foundUser) {
        final List<Task> tasks = validateTasks(foundUser);
        taskService.deleteAllUserTask(tasks);
        foundUser.setAllTasks(new ArrayList<>());
        userRepository.save(foundUser);
    }

    private static List<Task> validateTasks(User foundUser) {
        List<Task> tasks = foundUser.getAllTasks();
        if(tasks.isEmpty())throw new ToDoRunTimeException("No task found");
        return tasks;
    }

    @Override
    public List<Task> getAllTaskSByUser(viewAllTasksByUserRequest viewAllTasksByUserrequest) {
        User existinguser = findUserById(viewAllTasksByUserrequest.getUserId());
        validateIfUserIsNull(existinguser);
        if(!existinguser.isLoggedIn())throw  new ToDoRunTimeException("User not logged in");
        return taskService.getAllTasksByUser(existinguser.getId());
    }

    @Override
    public List<Task> findUserTasksByPriority(viewAllTasByPriorityRequest getTaskByPriorityRequest) {
        User existinguser = findUserById(getTaskByPriorityRequest.getUserId());
        validateIfUserIsNull(existinguser);
        ifUserLoggedIn(existinguser);
        return getAllTasks(getTaskByPriorityRequest);
    }

    private List<Task> getAllTasks(viewAllTasByPriorityRequest getTaskByPriorityRequest) {
        List<Task> allTasksByUser = taskService.findAllTaskByUser(getTaskByPriorityRequest.getUserId());
        List<Task> foundTasks = new ArrayList<>();
        for(Task task:allTasksByUser){if(task.getTaskPriority().equals(getTaskByPriorityRequest.getTaskPriority())){foundTasks.add(task);}}
        return foundTasks;
    }

    @Override
    public List<Task> getAllPendingTasksByUser(GetUserPendingTasksRequest getAllPendingTasksRequests) {
        return null;
    }

    @Override
    public UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest) {
        User foundUser = findUserById(updateUserRequest.getUserId());
        ifUserLoggedIn(foundUser);
        update(updateUserRequest, foundUser);
        return mapUpdateUserResponse(foundUser);
    }

    private void update(UpdateUserRequest updateUserRequest, User foundUser) {
        setProfile(updateUserRequest, foundUser);
        userRepository.delete(foundUser);
        userRepository.save(foundUser);
    }

    private static void setProfile(UpdateUserRequest updateUserRequest, User foundUser) {
        foundUser.setUserName(updateUserRequest.getUserName());
        foundUser.setPassword(updateUserRequest.getPassword());
        foundUser.setEmail(updateUserRequest.getEmail());
    }

    private static void ifUserLoggedIn(User foundUser) {
        if(!foundUser.isLoggedIn())throw new ToDoRunTimeException("User not logged in");
    }

    @Override
    public User getUserByEmail(GetUserRequest getUserRequest){
     User founduser = userRepository.findByEmail(getUserRequest.getEmail());
     if(founduser == null)throw new ToDoRunTimeException("User not found");
     return founduser;
    }


    @Override
    public DeleteAllUserResponse deleteAllUsers() {
        List<User> foundUsers = getAllUsers();
        if(foundUsers.isEmpty())throw new ToDoRunTimeException("No User found");
        userRepository.deleteAll();
        return mapDeleteAllUsersResponse();
    }


    @Override
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())throw  new ToDoRunTimeException("No user found");
        return allUsers;
    }

    @Override
    public User  findUserByUserName(String userName) {
        User foundUser = userRepository.findUserByUserName(userName.toLowerCase().trim());
        if(foundUser == null)throw new ToDoRunTimeException("User not found");
        return foundUser;
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ToDoRunTimeException("User not found"));
    }

    @Override
    public DeleteUserResponse deleteUserByUserName(DeleteUserRequest deleteUserRequest) {
        User foundUser = findUserByUserName(deleteUserRequest.getUserName());
        validateIfUserIsNull(foundUser);
        userRepository.deleteByUserName(deleteUserRequest.getUserName());
        return  mapDeleteUserResponse();
    }

    @Override
    public Task findTaskByTitle(GetTaskRequest getTaskRequest) {
        return   taskService.findAllTaskByUser(getTaskRequest.getUserId()).stream().filter(task -> task.getTitle().equals(getTaskRequest.getTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));

    }

    @Override
    public UpDateTaskResponse updateTask(UpDateTaskRequest upDateTaskRequest){
        User founduser = userRepository.findUserById(upDateTaskRequest.getUserId());
        validateIfUserIsNull(founduser);
        Task updatedTask = taskService.updateUserTask(upDateTaskRequest,founduser);
        Task existingTask = founduser.getAllTasks().stream().filter(task -> task.getTitle().equals(upDateTaskRequest.getTaskToBeUpdatedTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));

        founduser.getAllTasks().remove(existingTask);
        List<Task> userTask = founduser.getAllTasks();
        userTask.add(updatedTask);
        founduser.setAllTasks(userTask);
        return mapUDateTaskResponse();

    }



}
