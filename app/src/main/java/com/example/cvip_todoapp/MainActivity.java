package com.example.cvip_todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cvip_todoapp.adapter.MainAdapter;
import com.example.cvip_todoapp.databinding.ActivityMainBinding;
import com.example.cvip_todoapp.model_entity.Todo;
import com.example.cvip_todoapp.viewModel.Todo_ViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Todo_ViewModel viewModel;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Coders Cave Todo Apk");

        viewModel = new ViewModelProvider(MainActivity.this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(Todo_ViewModel.class);

        //goto add activity to add new Todo
        binding.addTodoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add_Update_Todo_Activity.class);
                intent.putExtra("type","new_todo");
                activityResultLauncher_forAddingTodo.launch(intent);
            }
        });

        //applying adapter to recyclerView
        binding.homeRv.setLayoutManager(new LinearLayoutManager(this));
        binding.homeRv.setHasFixedSize(true);
         adapter = new MainAdapter();
        binding.homeRv.setAdapter(adapter);

        //populating view
        viewModel.getTodoLiveList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.submitList(todos);
            }
        });

        // way to delete & update todos
        ActionForDelete_UpdateTodos();

    }

    private void ActionForDelete_UpdateTodos() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.RIGHT){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("WARNING !!");
                    builder.setMessage("Are you sure you want to delete Todo");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.deleteTodos(adapter.getTodo(viewHolder.getAdapterPosition()));
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            startActivity(getIntent());
                            finish();
                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            startActivity(getIntent());
                            finish();
                        }
                    });
                    builder.show().create();
                }

                if (direction == ItemTouchHelper.LEFT){
                    Intent intent = new Intent(getApplicationContext() , Add_Update_Todo_Activity.class);
                    intent.putExtra("type", "update_todo");
                    intent.putExtra("id",   adapter.getTodo(viewHolder.getAdapterPosition()).getId());
                    intent.putExtra("title",    adapter.getTodo(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("description",  adapter.getTodo(viewHolder.getAdapterPosition()).getDescription());
                    activityResultLauncher_forUpdatingTodo.launch(intent);
                }

            }
        }).attachToRecyclerView(binding.homeRv);
    }

    ActivityResultLauncher<Intent> activityResultLauncher_forAddingTodo =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            int resultCode = o.getResultCode();
            Intent data = o.getData();

            if (resultCode==RESULT_OK && data!=null){
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                Todo todo = new Todo(title,description);

                viewModel.insertTodos(todo);
                Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
            } else if (resultCode != RESULT_CANCELED && data!=null){
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }

            if (resultCode==RESULT_OK || resultCode==RESULT_CANCELED){
                startActivity(getIntent());
                finish();
            }

        }
    });

    ActivityResultLauncher<Intent>activityResultLauncher_forUpdatingTodo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            int resultCode = o.getResultCode();
            Intent data = o.getData();

            if (resultCode==RESULT_OK && data!=null){
                int id = data.getIntExtra("id",0);
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                Todo todo = new Todo(title,description);
                todo.setId(id);

                viewModel.updateTodos(todo);
                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();

            }else if (resultCode != RESULT_CANCELED && data!=null){
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }

            if (resultCode==RESULT_OK || resultCode==RESULT_CANCELED){
                startActivity(getIntent());
                finish();
            }

        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_refresh){
            startActivity(getIntent());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}