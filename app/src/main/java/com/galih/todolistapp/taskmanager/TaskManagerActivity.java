package com.galih.todolistapp.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.galih.todolistapp.R;
import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.databinding.ActivityTaskManagerBinding;
import com.galih.todolistapp.task.TaskActivity;
import com.galih.todolistapp.utils.DatePickerFragment;
import com.galih.todolistapp.utils.Utils;
import com.galih.todolistapp.utils.ViewModelFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaskManagerActivity extends AppCompatActivity implements DatePickerFragment.DialogDateListener {

    private ActivityTaskManagerBinding binding;
    private TaskManagerViewModel viewModel;
    private boolean isEdit;
    private Task task;
    private long dueDateMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskManagerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = obtainViewModel(TaskManagerActivity.this);
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);
        dueDateMillis = Utils.getTodayMillis();
        getSupportActionBar().setTitle("Tambah Todo");

        if (isEdit) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int id = intent.getIntExtra("id", 0);
                String title = intent.getStringExtra("title");
                String desc = intent.getStringExtra("desc");
                long dueDate = intent.getLongExtra("dueDate", 0);
                boolean isCompleted = intent.getBooleanExtra("isCompleted", false);
                task = new Task(id, title, desc, dueDate, isCompleted);
                populateData();

                getSupportActionBar().setTitle("Edit Todo");
                binding.btnSave.setText(getResources().getText(R.string.simpan_perubahan));
                binding.btnDelete.setVisibility(View.VISIBLE);
            }
        }

        binding.btnDueDate.setOnClickListener(v -> showDatePicker());
        binding.btnSave.setOnClickListener(v -> handleSave());
        binding.btnDelete.setOnClickListener(v -> handleDelete());
    }

    private void showDatePicker() {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void populateData() {
        binding.etTitle.setText(task.getTitle());
        binding.etDesc.setText(task.getDescription());
        binding.tvDueDate.setText(Utils.getDateFromMillis(task.getDueDateMillis()));
        dueDateMillis = task.getDueDateMillis();
    }

    private void handleSave() {
        String title = binding.etTitle.getText().toString();
        String desc = binding.etDesc.getText().toString();

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Title dan Descripction tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else if (isEdit) {
            task.setTitle(title);
            task.setDescription(desc);
            task.setDueDateMillis(dueDateMillis);
        } else {
            task = new Task(title, desc, dueDateMillis);
        }

        viewModel.upsert(task);
        goToTask();
    }

    private void handleDelete() {
        viewModel.delete(task);
        goToTask();
    }

    private void goToTask() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
        finish();
    }

    @NonNull
    private static TaskManagerViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(TaskManagerViewModel.class);
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        binding.tvDueDate.setText(dateFormat.format(calendar.getTime()));
        dueDateMillis = calendar.getTimeInMillis();
    }
}