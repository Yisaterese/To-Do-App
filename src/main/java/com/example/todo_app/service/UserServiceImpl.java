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
        validateRegisterRequestIsNotBlank(createTaskRequest);
        userRepository.findAll().forEach(user ->{if(user.getEmail().equals(createTaskRequest.getEmail()))throw new ToDoRunTimeException("email taken");});
        final User newUser = getUserCreated(createTaskRequest);
        return mapRegisterResponse(newUser);
    }

    private static void validateRegisterRequestIsNotBlank(RegisterUserRequest createTaskRequest) {
        if(createTaskRequest.getEmail().isBlank())throw new ToDoRunTimeException("Please enter a valid email address");
        if(createTaskRequest.getUserName().isBlank())throw new ToDoRunTimeException("Please enter a valid username");
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
        findAndValidateUser(loginRequest, existingUser);
        userRepository.save(existingUser);
        return  mapLoginResponse(existingUser);
    }

    private void findAndValidateUser(LoginRequest loginRequest, User existingUser) {
        validateIfUserIsNull(existingUser);
        validateAlreadyLoggedIn(existingUser);
        setUserProperties(loginRequest, existingUser);
    }


    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        final User foundUser = getUser(createTaskRequest);
        foundUser.getAllTasks().forEach(task -> {if(task.getTitle().equals(createTaskRequest.getTitle().trim()))throw new ToDoRunTimeException(foundUser.getUserName()+" already have task with title "+createTaskRequest.getTitle());});
        validateUserLogin(foundUser);
        final Task createdTask = getCreatedTask(createTaskRequest, foundUser);
        return mapCreateTaskResponse(createdTask);
    }

    @Override
    public DeleteAllTaskResponse deleteAllTasks() {
        return null;
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
        foundUser.getPendingTasks().forEach(task -> {if(task.getTitle().equals(createdTask.getTitle()))throw new ToDoRunTimeException("Task with "+createdTask.getTitle()+" already exist");});
        foundUser.setPendingTasks(tasks);
        userRepository.save(foundUser);
    }

    private User getUser(CreateTaskRequest createTaskRequest) {
        User foundUser = userRepository.findUserById(createTaskRequest.getUserId());
        if (foundUser == null) throw new ToDoRunTimeException("User not found");
        return foundUser;
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
        findAndValidateUser(logOutRequest, existingUser);
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

    private static void findAndValidateUser(LogOutRequest logOutRequest, User existingUser) {
        if(!existingUser.getId().equals(logOutRequest.getUserId()))throw new ToDoRunTimeException("user not found");
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        User assignee = findUserById(assignTaskRequest.getAssigneeUserId());
        User assigner = findUserById(assignTaskRequest.getAssignerUserId());
        assignTaskToAssigneeAndNotify(assignTaskRequest, assignee, assigner);
        return mapAssignTaskResponse(assigner, assignee);
    }

    private void assignTaskToAssigneeAndNotify(AssignTaskRequest assignTaskRequest, User assignee, User assigner) {
        findAndValidateUser(assignee, assigner);
        assignTaskToAssignee(assignee, assignTaskRequest, assigner);
        messageSender.assignTaskMessage(assignee, assignTaskRequest.getTitle());
    }

    private void assignTaskToAssignee(User assignee,AssignTaskRequest assignTaskRequest, User assigner) {
        final Task assignerTask_toBeAssigned = getTask(assigner, assignTaskRequest.getTitle().toLowerCase().trim());
        assign_taskTo(assignee, assignerTask_toBeAssigned);
        deleteAssignedTaskFromAssigner(assignerTask_toBeAssigned, assigner);
    }

    private void assign_taskTo(User assignee, Task assignerTask_toBeAssigned) {
        final List<Task> assigneeTasks = addTaskToBeAssigned_toAssignee(assignee, assignerTask_toBeAssigned);
        assignee.setAllTasks(assigneeTasks);
        assignee.setPendingTasks(assigneeTasks);
        userRepository.save(assignee);
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
        assigner.setAllTasks(assignerTasks);
        removeTaskFromPendingTaskList(assignerTask_toBeAssigned, assigner, assignerTasks);
        userRepository.save(assigner);
    }

    private static void removeTaskFromPendingTaskList(Task assignerTask_toBeAssigned, User assigner, List<Task> assignerTasks) {
        List<Task> pendingTasks = assigner.getPendingTasks();
        pendingTasks.remove(assignerTask_toBeAssigned);
        assigner.setPendingTasks(assignerTasks);
    }

    private static void findAndValidateUser(User assignee, User assigner) {
        validateIfUserIsNull(assignee);
        validateIfUserIsNull(assigner);
        validateUserLogin(assigner);
    }

    @Override
    public ShareTaskResponse shareTask(ShareTaskRequest shareTaskRequest) {
        User assignee = findUserById(shareTaskRequest.getAssigneeId());
        User assigner = findUserById(shareTaskRequest.getAssignerId());
        findAndValidateUser(assignee, assigner);
        shareTask(shareTaskRequest, assigner, assignee);
        messageSender.shareTaskMessage(assignee, shareTaskRequest.getTitle());
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
        final User foundUser = findUser(deleteTaskByUserRequest);
        validateIfUserIsNull(foundUser);
        deleteAllUserTasks(foundUser);
        return mapDeleteTasksResponse();
    }

    private void deleteAllUserTasks(User foundUser) {
        final List<Task> allTasks = getAllUserTasks(foundUser);
        allTasks.clear();
        userRepository.save(foundUser);
        taskService.deleteAllTasks(foundUser);
    }

    private static List<Task> getAllUserTasks(User foundUser) {
        List<Task> allTasks = foundUser.getAllTasks();
        if(allTasks.isEmpty())throw  new ToDoRunTimeException("No task found for user");
        return allTasks;
    }

    private User findUser(DeleteAllTaskByUserRequest deleteTaskByUserRequest) {
        User foundUser = findUserById(deleteTaskByUserRequest.getUserId());
        if (foundUser.getAllTasks() == null) throw new IllegalStateException("No task found for user");
        return foundUser;
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
    public CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest) {
        User user = findUserById(completeTaskRequest.getUserId());
        completeTask(completeTaskRequest, user);
        return mapCompleteTaskResponse();
    }

    private void completeTask(CompleteTaskRequest completeTaskRequest, User user) {
        Task completedTask = user.getAllTasks().stream().filter(task -> task.getTitle().equals(completeTaskRequest.getTitle().toLowerCase().trim())).findFirst().orElseThrow(() -> new ToDoRunTimeException("Task not found"));
        List<Task> allUserTasks = user.getAllTasks();
        allUserTasks.remove(completedTask);
        user.setAllTasks(allUserTasks);
        addPendingTask(user, completedTask);
    }

    private void addPendingTask(User user, Task completedTask) {
        List<Task> allUserTasks;
        allUserTasks = user.getPendingTasks();
        allUserTasks.remove(completedTask);
        user.setPendingTasks(allUserTasks);
        userRepository.save(user);
    }

    @Override
    public List<Task> findUserTasksByPriority(viewAllTasByPriorityRequest getTaskByPriorityRequest) {
        User existinguser = findUserById(getTaskByPriorityRequest.getUserId());
        validateIfUserIsNull(existinguser);
        validateUserLogin(existinguser);
        return getAllUserTasks(getTaskByPriorityRequest);
    }

    private List<Task> getAllUserTasks(viewAllTasByPriorityRequest getTaskByPriorityRequest) {
        List<Task> allTasksByUser = taskService.findAllTaskByUser(getTaskByPriorityRequest.getUserId());
        List<Task> foundTasks = new ArrayList<>();
        for(Task task:allTasksByUser){if(task.getTaskPriority().equals(getTaskByPriorityRequest.getTaskPriority())){foundTasks.add(task);}}
        return foundTasks;
    }

    @Override
    public List<Task> viewAllPendingTasksByUser(GetUserPendingTasksRequest getAllPendingTasksRequests) {
        User existingUser = findUserById(getAllPendingTasksRequests.getUserId());
        validateIfUserIsNull(existingUser);
        return  getAllPendingTasks(existingUser);
    }

    private List<Task> getAllPendingTasks(User existingUser) {
       List<Task> allTasks = existingUser.getPendingTasks();
       if(allTasks.isEmpty())throw new ToDoRunTimeException("No pending task found");
       return allTasks;
    }

    @Override
    public UpdateUserResponse updateUserProfile(UpdateUserRequest updateUserRequest) {
        User foundUser = findUserById(updateUserRequest.getUserId());
        validateUserLogin(foundUser);
        update(updateUserRequest, foundUser);
        return mapUpdateUserResponse(foundUser);
    }

    private void update(UpdateUserRequest updateUserRequest, User foundUser) {
        setProfile(updateUserRequest, foundUser);
        userRepository.delete(foundUser);
        userRepository.save(foundUser);
    }

    private static void setProfile(UpdateUserRequest updateUserRequest, User foundUser) {
        foundUser.setUserName(updateUserRequest.getUserNameTBeUpdated());
        foundUser.setPassword(updateUserRequest.getPasswordToBeUpdated());
        foundUser.setEmail(updateUserRequest.getEmailToBeUpdated());
    }
    private static void validateUserLogin(User foundUser) {
        if(!foundUser.isLoggedIn())throw new ToDoRunTimeException("User not logged in");
    }

    @Override
    public User getUserByEmail(GetUserRequest getUserRequest){
     User founduser = userRepository.findByEmail(getUserRequest.getEmail());
     if(founduser == null)throw new ToDoRunTimeException("User not found");
     return founduser;
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
        return userRepository.findById(id).orElseThrow(()-> new ToDoRunTimeException("User not found"));
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
        List<Task> userTasks = taskService.findAllTaskByUser(getTaskRequest.getUserId());
        return userTasks.stream().filter(task -> task.getTitle().equals(getTaskRequest.getTitle().toLowerCase().trim())).findFirst().orElseThrow(()-> new ToDoRunTimeException("No task found"));
    }
    @Override
    public UpDateTaskResponse updateTask(UpDateTaskRequest upDateTaskRequest){
        final User founduser = findAndValidateUser(upDateTaskRequest);
        taskService.updateUserTask(upDateTaskRequest,founduser);
        return mapUDateTaskResponse();
    }

    private User findAndValidateUser(UpDateTaskRequest upDateTaskRequest) {
        User founduser = userRepository.findUserById(upDateTaskRequest.getUserId());
        validateIfUserIsNull(founduser);
        return founduser;
    }

    private  void updateTask(Task existingTask,User foundUser,Task upDatedTask ){
        foundUser .getAllTasks().remove(existingTask);
        List<Task> userTask = foundUser .getAllTasks();
        userTask.add(upDatedTask);
        foundUser .setAllTasks(userTask);
        userRepository.save(foundUser);

    };



}
