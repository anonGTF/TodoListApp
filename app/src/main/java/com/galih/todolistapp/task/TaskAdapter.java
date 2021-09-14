package com.galih.todolistapp.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.galih.todolistapp.data.model.Task;
import com.galih.todolistapp.databinding.ItemTaskBinding;
import com.galih.todolistapp.taskmanager.TaskManagerActivity;
import com.galih.todolistapp.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<Task> listTask = new ArrayList<>();
    private final Activity activity;
    private final BiFunction<Task, Boolean, Void> onCheckedChange;

    public TaskAdapter(Activity activity, BiFunction<Task, Boolean, Void> onCheckedChange) {
        this.activity = activity;
        this.onCheckedChange = onCheckedChange;
    }

    public void setListTask(List<Task> listNotes) {
        final TaskDiffCallback diffCallback = new TaskDiffCallback(this.listTask, listNotes);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listTask.clear();
        this.listTask.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.TaskViewHolder holder, int position) {
        holder.bind(listTask.get(position), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        final ItemTaskBinding binding;

        TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Task task, View view) {
            binding.tvTitle.setText(task.getTitle());
            binding.tvDueDate.setText(Utils.getDateFromMillis(task.getDueDateMillis()));
            binding.cbStatus.setChecked(task.isCompleted());

            binding.cbStatus.setOnClickListener(v -> onCheckedChange.apply(task, !task.isCompleted()));

            view.setOnClickListener(v -> {
                Intent intent = new Intent(activity, TaskManagerActivity.class);
                intent.putExtra("id", task.getId());
                intent.putExtra("title", task.getTitle());
                intent.putExtra("desc", task.getDescription());
                intent.putExtra("dueDate", task.getDueDateMillis());
                intent.putExtra("isCompleted", task.isCompleted());
                intent.putExtra("isEdit", true);
                activity.startActivity(intent);
            });
        }
    }
}
