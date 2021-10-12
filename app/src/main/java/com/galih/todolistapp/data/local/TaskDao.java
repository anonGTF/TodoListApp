package com.galih.todolistapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.data.model.UserWithTask;

import java.util.List;

@Dao
public interface TaskDao {

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<UserWithTask> getAllTasks(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("UPDATE task SET completed = :completed WHERE id = :taskId")
    void updateCompleted(int taskId, boolean completed);
}
