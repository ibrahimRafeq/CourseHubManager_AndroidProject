package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursehubmanager_androidproject.databinding.ItemLessonsBinding;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Lessons> lessonsList;
    private ItemLessonsBinding binding;
    private OnItemClick itemClick;

    public LessonsAdapter(Context context, List<Lessons> lessonsList, OnItemClick itemClick) {
        this.context = context;
        this.lessonsList = lessonsList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemLessonsBinding.inflate(LayoutInflater.from(context), parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Lessons lessons = lessonsList.get(position);

        myViewHolder.binding.title.setText(lessons.getTitle());
        myViewHolder.binding.URL.setText(lessons.getURL());
        myViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onCourseClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemLessonsBinding binding;
        public MyViewHolder(ItemLessonsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClick{
        void onCourseClicked(int position);
    }
}
