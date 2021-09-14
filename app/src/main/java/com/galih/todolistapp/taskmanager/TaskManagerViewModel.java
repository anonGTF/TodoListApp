package com.galih.todolistapp.taskmanager;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import com.galih.todolistapp.data.TaskRepository;
import com.galih.todolistapp.data.model.Task;

public class TaskManagerViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public TaskManagerViewModel(Application application) {
        taskRepository = new TaskRepository(application);
    }

    public void upsert(Task task) {
        taskRepository.insert(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
