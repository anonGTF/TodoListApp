package com.galih.todolistapp.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.galih.todolistapp.data.local.TaskDao;
import com.galih.todolistapp.data.local.TaskDatabase;
import com.galih.todolistapp.data.local.UserDao;
import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.data.model.User;
import com.galih.todolistapp.data.model.UserWithTask;
import com.galih.todolistapp.data.preference.Preference;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final UserDao userDao;
    private final Preference preference;
    private final ExecutorService executorService;

    public TaskRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDao = db.taskDao();
        userDao = db.userDao();
        preference = new Preference(application);
    }

    public LiveData<UserWithTask> getAllTask(int userId) {
        return taskDao.getAllTasks(userId);
    }

    public void insert(final Task task) {
        executorService.execute(() -> taskDao.insertTask(task));
    }

    public void delete(final Task task){
        executorService.execute(() -> taskDao.deleteTask(task));
    }

    public void updateCompleted(int id, boolean completed) {
        executorService.execute(() -> taskDao.updateCompleted(id, completed));
    }

    public LiveData<User> login(String email, String password) {
        return userDao.login(email, password);
    }

    public void register(final User user) {
        executorService.execute(() -> userDao.register(user));
    }

    public boolean isLoggedIn() {
        return preference.loggedin();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        preference.setLoggedin(isLoggedIn);
    }

    public void setUserId(int userId){
        preference.setUserId(userId);
    }

    public int getUserId(){
        return preference.getUserId();
    }

    public void setUserName(String userName){
        preference.setUserName(userName);
    }

    public String getUserName(){
        return preference.getUserName();
    }
}
