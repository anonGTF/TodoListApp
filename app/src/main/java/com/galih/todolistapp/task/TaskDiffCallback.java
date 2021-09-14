package com.galih.todolistapp.task;

import androidx.recyclerview.widget.DiffUtil;

import com.galih.todolistapp.data.model.Task;

import java.util.List;

public class TaskDiffCallback extends DiffUtil.Callback {
    private final List<Task> mOldTaskList;
    private final List<Task> mNewTaskList;

    public TaskDiffCallback(List<Task> oldTaskList, List<Task> newTaskList) {
        this.mOldTaskList = oldTaskList;
        this.mNewTaskList = newTaskList;
    }

    @Override
    public int getOldListSize() {
        return mOldTaskList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewTaskList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldTaskList.get(oldItemPosition).getId() == mNewTaskList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Task oldTask = mOldTaskList.get(oldItemPosition);
        final Task newTask = mNewTaskList.get(newItemPosition);
        return oldTask.getTitle().equals(newTask.getTitle()) && oldTask.getDescription().equals(newTask.getDescription());
    }
}
