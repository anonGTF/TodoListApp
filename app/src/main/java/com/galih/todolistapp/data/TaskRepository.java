package com.galih.todolistapp.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.galih.todolistapp.data.local.TaskDao;
import com.galih.todolistapp.data.local.TaskDatabase;
import com.galih.todolistapp.data.model.Task;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final ExecutorService executorService;

    public TaskRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDao = db.taskDao();
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTasks();
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
}
