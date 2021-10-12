package com.galih.todolistapp.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithTask {

    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Task> tasks;

}
