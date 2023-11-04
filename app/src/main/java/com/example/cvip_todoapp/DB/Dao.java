package com.example.cvip_todoapp.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cvip_todoapp.model_entity.Todo;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    public void insertTodo(Todo todo);
    @Update
    public void updateTodo(Todo todo);
    @Delete
    public void deleteTodo(Todo todo);
    @Query("SELECT * FROM todo_app")
    public LiveData<List<Todo>> getAllTodo(Todo todo);

}
