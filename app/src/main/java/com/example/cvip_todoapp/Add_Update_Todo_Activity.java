package com.example.cvip_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.cvip_todoapp.databinding.ActivityAddUpdateTodoBinding;

import java.util.Objects;

public class Add_Update_Todo_Activity extends AppCompatActivity {
    ActivityAddUpdateTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUpdateTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");

        // if user is in Add Mode
        if (Objects.requireNonNull(type).equals("new_todo")){
            Objects.requireNonNull(getSupportActionBar()).setTitle("Add New Todo");

            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.titleEt.getText().toString().trim());
                    intent.putExtra("description", binding.descriptionEt.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }

        // if user is in Update Mode
        if (Objects.requireNonNull(type).equals("update_todo")){
            Objects.requireNonNull(getSupportActionBar()).setTitle("Update Todo");

            int id = getIntent().getIntExtra("id",0);

            binding.titleEt.setText( getIntent().getStringExtra("title") );
            binding.descriptionEt.setText( getIntent().getStringExtra("description") );

            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("title", binding.titleEt.getText().toString().trim() );
                    intent.putExtra("description", binding.descriptionEt.getText().toString().trim() );
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }

    }
}