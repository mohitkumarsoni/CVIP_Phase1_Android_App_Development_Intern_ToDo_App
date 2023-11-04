package com.example.cvip_todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView home_rv;
    FloatingActionButton add_todo_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Coders Cave Todo Apk");
        initViews();
    }

    private void initViews() {
        home_rv = findViewById(R.id.home_rv);
        add_todo_fab = findViewById(R.id.add_todo_fab);
    }
}