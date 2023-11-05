package com.example.cvip_todoapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cvip_todoapp.DB.Dao;
import com.example.cvip_todoapp.DB.Database;
import com.example.cvip_todoapp.model_entity.Todo;

import java.util.List;

public class Repository {

    private Dao dao;
    private LiveData<List<Todo>> todoList;

    public Repository(Application application) {
        Database database = Database.getInstance(application);
        dao = database.dao();
        todoList = dao.getAllTodo();
    }

    public void insertData(Todo todo){
        new InsertTask(dao).insert(todo);
    }
    public void updateData(Todo todo){
        new UpdateTask(dao).update(todo);
    }
    public void deleteData(Todo todo){
        new DeleteTask(dao).delete(todo);
    }
    public LiveData<List<Todo>> getTodoList(){
        return todoList;
    }

    //================================================================================

    private static class InsertTask{
        private Dao dao;
        InsertTask(Dao dao) {
            this.dao = dao;
        }
        void insert(Todo todo){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dao.insertTodo(todo);
                }
            }).start();
        }
    }

    private static class UpdateTask{
        private Dao dao;
        public UpdateTask(Dao dao) {
            this.dao = dao;
        }
        void update(Todo todo){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dao.updateTodo(todo);
                }
            }).start();
        }
    }

    private static class DeleteTask{
        private Dao dao;
        public DeleteTask(Dao dao) {
            this.dao = dao;
        }
        void delete(Todo todo){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dao.deleteTodo(todo);
                }
            }).start();
        }
    }

}
