package com.galih.todolistapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dueDate")
    private long dueDateMillis;

    @ColumnInfo(name = "completed")
    private boolean isCompleted;

    @ColumnInfo(name = "userId")
    private int userId;

    @Ignore
    public Task(int id, String title, String description, long dueDateMillis, boolean isCompleted, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDateMillis = dueDateMillis;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }

    public Task(String title, String description, long dueDateMillis, int userId) {
        this.title = title;
        this.description = description;
        this.dueDateMillis = dueDateMillis;
        this.isCompleted = false;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDueDateMillis() {
        return dueDateMillis;
    }

    public void setDueDateMillis(long dueDateMillis) {
        this.dueDateMillis = dueDateMillis;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
