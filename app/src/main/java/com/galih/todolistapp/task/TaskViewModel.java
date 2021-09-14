package com.galih.todolistapp.task;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.galih.todolistapp.data.TaskRepository;
import com.galih.todolistapp.data.model.Task;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public TaskViewModel(Application application) {
        taskRepository = new TaskRepository(application);
    }

    public void updateCompleted(int id, boolean isCompleted) {
        taskRepository.updateCompleted(id, isCompleted);
    }

    public LiveData<List<Task>> getAllTask() {
        return taskRepository.getAllTask();
    }
}
