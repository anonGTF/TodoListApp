package com.galih.todolistapp.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.data.model.User;

@Database(
        entities = {Task.class, User.class},
        version = 1,
        exportSchema = false
)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract UserDao userDao();

    private static volatile TaskDatabase INSTANCE;

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TaskDatabase.class, "task_database")
                        .build();
            }
        }
        return INSTANCE;
    }

}
