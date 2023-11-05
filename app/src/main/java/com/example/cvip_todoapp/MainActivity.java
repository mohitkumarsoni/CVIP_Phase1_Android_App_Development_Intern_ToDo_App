package com.example.cvip_todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cvip_todoapp.databinding.ActivityMainBinding;
import com.example.cvip_todoapp.model_entity.Todo;
import com.example.cvip_todoapp.viewModel.Todo_ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Todo_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Coders Cave Todo Apk");

        viewModel = new ViewModelProvider(MainActivity.this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(Todo_ViewModel.class);

        binding.addTodoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add_Update_Todo_Activity.class);
                intent.putExtra("type","new_todo");
                activityResultLauncher_forAddingTodo.launch(intent);
            }
        });

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
            } else {
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
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