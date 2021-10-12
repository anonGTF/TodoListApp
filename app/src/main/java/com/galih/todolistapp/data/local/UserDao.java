package com.galih.todolistapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.galih.todolistapp.data.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    LiveData<User> login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> isAlreadyExist(String email);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long register(User user);

}
