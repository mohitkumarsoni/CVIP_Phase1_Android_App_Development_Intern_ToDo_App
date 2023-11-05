package com.example.cvip_todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvip_todoapp.R;
import com.example.cvip_todoapp.databinding.EachRvBinding;
import com.example.cvip_todoapp.model_entity.Todo;

public class MainAdapter extends ListAdapter<Todo, MainAdapter.MyViewHolder> {
    public MainAdapter(){
        super(CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Todo> CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())    &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Todo todo =getItem(position);
        holder.binding.todoTitleTv.setText(todo.getTitle());
        holder.binding.todoDescriptionTv.setText(todo.getDescription());
    }

    public Todo getTodo(int position){
        Todo todo = getItem(position);
        return todo;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EachRvBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EachRvBinding.bind(itemView);
        }
    }

}
