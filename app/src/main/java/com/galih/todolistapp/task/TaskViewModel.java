package com.galih.todolistapp.task;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.galih.todolistapp.data.TaskRepository;
import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.data.model.UserWithTask;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public TaskViewModel(Application application) {
        taskRepository = new TaskRepository(application);
    }

    public void updateCompleted(int id, boolean isCompleted) {
        taskRepository.updateCompleted(id, isCompleted);
    }

    public LiveData<UserWithTask> getAllTask(int userId) {
        return taskRepository.getAllTask(userId);
    }

    public int getUserId() {
        return taskRepository.getUserId();
    }

    public String getUserName() {
        return taskRepository.getUserName();
    }

    public void logout() {
        taskRepository.setUserId(0);
        taskRepository.setLoggedIn(false);
        taskRepository.setUserName("");
    }
}
