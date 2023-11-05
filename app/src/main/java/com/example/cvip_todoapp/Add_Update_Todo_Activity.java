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

        if (Objects.requireNonNull(type).equals("update_todo"));

    }
}