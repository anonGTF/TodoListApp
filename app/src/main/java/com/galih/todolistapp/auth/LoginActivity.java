package com.galih.todolistapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.galih.todolistapp.data.model.User;
import com.galih.todolistapp.databinding.ActivityLoginBinding;
import com.galih.todolistapp.task.TaskActivity;
import com.galih.todolistapp.utils.Utils;
import com.galih.todolistapp.utils.ViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Login");

        viewModel = obtainViewModel(this);

        if (viewModel.isLoggedIn()) {
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        }

        binding.btnLogin.setOnClickListener(v -> login());
        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Utils.showMessage(this, "Email, dan Password harus diisi");
        } else {
            viewModel.login(email, password).observe(this, observer);
        }
    }

    private final Observer<User> observer = new Observer<User>() {

        @Override
        public void onChanged(User user) {
            if (user != null) {
                viewModel.setLoggedIn(true);
                viewModel.setUserId(user.getId());
                viewModel.setUserName(user.getName());
                Intent intent = new Intent(LoginActivity.this, TaskActivity.class);
                startActivity(intent);
            } else {
                Utils.showMessage(LoginActivity.this, "User tidak ditemukan");
            }
        }
    };

    @NonNull
    private static AuthViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(AuthViewModel.class);
    }
}