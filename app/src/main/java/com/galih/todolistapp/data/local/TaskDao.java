package com.galih.todolistapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.galih.todolistapp.data.model.Task;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY dueDate DESC")
    LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("UPDATE task SET completed = :completed WHERE id = :taskId")
    void updateCompleted(int taskId, boolean completed);
}
