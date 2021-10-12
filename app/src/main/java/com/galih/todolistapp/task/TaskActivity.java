package com.galih.todolistapp.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.galih.todolistapp.R;
import com.galih.todolistapp.auth.LoginActivity;
import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.data.model.UserWithTask;
import com.galih.todolistapp.taskmanager.TaskManagerActivity;
import com.galih.todolistapp.databinding.ActivityTaskBinding;
import com.galih.todolistapp.utils.ViewModelFactory;

import java.util.List;
import java.util.function.BiFunction;

public class TaskActivity extends AppCompatActivity {

    private ActivityTaskBinding binding;
    private TaskAdapter taskAdapter;
    private TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setupRecyclerView();
        getSupportActionBar().setTitle("List Todo");

        viewModel = obtainViewModel(TaskActivity.this);
        viewModel.getAllTask(viewModel.getUserId()).observe(this, taskObserver);

        String greeting = String.format(getResources().getString(R.string.halo_name), viewModel.getUserName());
        binding.tvGreeting.setText(greeting);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), TaskManagerActivity.class);
            intent.putExtra("isEdit", false);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mi_logout) {
            viewModel.logout();
            Intent intent = new Intent(TaskActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @NonNull
    private static TaskViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(TaskViewModel.class);
    }

    private final Observer<UserWithTask> taskObserver = new Observer<UserWithTask>() {
        @Override
        public void onChanged(UserWithTask userWithTask) {
            if (userWithTask.tasks != null) {
                taskAdapter.setListTask(userWithTask.tasks);
            }
        }
    };

    private void setupRecyclerView() {
        BiFunction<Task, Boolean, Void> onCheckedChange = (Task task, Boolean isCompleted) -> {
            viewModel.updateCompleted(task.getId(), isCompleted);
            return null;
        };
        taskAdapter = new TaskAdapter(this, onCheckedChange);
        RecyclerView rv = binding.rvTask;
        rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rv.setAdapter(taskAdapter);
    }
}