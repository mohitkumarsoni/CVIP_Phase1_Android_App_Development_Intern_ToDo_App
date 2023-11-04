package com.example.cvip_todoapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cvip_todoapp.DB.Dao;
import com.example.cvip_todoapp.DB.Database;
import com.example.cvip_todoapp.model_entity.Todo;

import java.util.List;

public class Todo_Repository {
    private Dao dao;
    private LiveData<List<Todo>> todoList;

    public Todo_Repository(Application application){
        Database database = Database.getInstance(application);
        dao = database.dao();
        todoList = dao.getAllTodo();
    }

    public void insertData(Todo todo){
        new InsertTask(dao).execute(todo);
    }
    public void updateData(Todo todo){
        new UpdateTask(dao).execute(todo);
    }
    public void deleteData(Todo todo){
        new DeleteTask(dao).execute(todo);
    }
    public LiveData<List<Todo>> getTodoList(){
        return todoList;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static class InsertTask extends AsyncTask<Todo, Void, Void>{
        private Dao dao;
        public InsertTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            dao.insertTodo(todos[0]);
            return null;
        }
    }

    public static class UpdateTask extends AsyncTask<Todo, Void, Void>{
        private Dao dao;
        public UpdateTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            dao.updateTodo(todos[0]);
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<Todo, Void, Void>{
        private Dao dao;
        public DeleteTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            dao.deleteTodo(todos[0]);
            return null;
        }
    }
}
