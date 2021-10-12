package com.galih.todolistapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.galih.todolistapp.data.model.User;
import com.galih.todolistapp.databinding.ActivityRegisterBinding;
import com.galih.todolistapp.task.TaskActivity;
import com.galih.todolistapp.utils.Utils;
import com.galih.todolistapp.utils.ViewModelFactory;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Register");

        viewModel = obtainViewModel(this);

        if (viewModel.isLoggedIn()) {
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        }

        binding.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.btnRegister.setOnClickListener(v -> register());
    }

    private void register() {
        String name = binding.etName.getText().toString();
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Utils.showMessage(this, "Nama, Email, dan Password harus diisi");
        } else {
            viewModel.isAlreadyExist(email).observe(this, observer);
        }
    }

    private final Observer<User> observer = new Observer<User>() {

        @Override
        public void onChanged(User user) {
            String name = binding.etName.getText().toString();
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();

            Log.d("coba", "onChanged: " + user);

            if (user == null) {
                viewModel.register(name, email, password);
                Utils.showMessage(RegisterActivity.this,  "Akun " + name + " berhasil dibuat");
                finish();
            } else {
                binding.etEmail.setError("User sudah memiliki akun");
            }
        }
    };

    @NonNull
    private static AuthViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(AuthViewModel.class);
    }
}