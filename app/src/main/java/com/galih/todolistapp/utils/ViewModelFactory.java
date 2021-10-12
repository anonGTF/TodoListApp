package com.galih.todolistapp.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.galih.todolistapp.auth.AuthViewModel;
import com.galih.todolistapp.task.TaskViewModel;
import com.galih.todolistapp.taskmanager.TaskManagerViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final Application mApplication;
    private ViewModelFactory(Application application) {
        mApplication = application;
    }
    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application);
            }
        }
        return INSTANCE;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(TaskManagerViewModel.class)) {
            return (T) new TaskManagerViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
