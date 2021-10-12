package com.galih.todolistapp.auth;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.galih.todolistapp.data.TaskRepository;
import com.galih.todolistapp.data.model.User;

public class AuthViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public AuthViewModel(Application application) {
        taskRepository = new TaskRepository(application);
    }

    public LiveData<User> login(String email, String password) {
        return taskRepository.login(email, password);
    }

    public void register(String name, String email, String password) {
        taskRepository.register(new User(name, email, password));
    }

    public LiveData<User> isAlreadyExist(String email) {
        return taskRepository.isAlreadyExist(email);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        taskRepository.setLoggedIn(isLoggedIn);
    }

    public void setUserName(String name) {
        taskRepository.setUserName(name);
    }

    public boolean isLoggedIn() {
        return taskRepository.isLoggedIn();
    }

    public void setUserId(int userId) {
        taskRepository.setUserId(userId);
    }
}
