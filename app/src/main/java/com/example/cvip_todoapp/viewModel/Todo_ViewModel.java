package com.example.cvip_todoapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cvip_todoapp.model_entity.Todo;
import com.example.cvip_todoapp.repository.Repository;
import com.example.cvip_todoapp.repository.Todo_Repository;

import java.util.List;

public class Todo_ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Todo>> todoLiveList;

    public Todo_ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        todoLiveList = repository.getTodoList();
    }

    public void insertTodos(Todo todo){
        repository.insertData(todo);
    }
    public void updateTodos(Todo todo){
        repository.updateData(todo);
    }
    public void deleteTodos(Todo todo){
        repository.deleteData(todo);
    }
    public LiveData<List<Todo>> getTodoLiveList (){
        return todoLiveList;
    }

}
