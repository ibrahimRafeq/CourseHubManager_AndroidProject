package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager_androidproject.databinding.ItemCourseBinding;
import com.example.coursehubmanager_androidproject.databinding.ItemCourseBookMarkBinding;

import java.util.List;

public class MarkBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Course> courseList;
    ItemCourseBookMarkBinding markBinding;
    OnItemClick itemClick;

    public MarkBookAdapter(Context context, List<Course> courseList, OnItemClick itemClick) {
        this.context = context;
        this.courseList = courseList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        markBinding = ItemCourseBookMarkBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(markBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Course course = courseList.get(position);
        myViewHolder.markBinding.nameCourseTV.setText(course.getCourseName());
        myViewHolder.markBinding.instructorName.setText(course.getCourseLecturer());
        myViewHolder.markBinding.description.setText(course.getCourseDetails());
        myViewHolder.markBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onCourseClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseBookMarkBinding markBinding;

        public MyViewHolder(ItemCourseBookMarkBinding markBinding) {
            super(markBinding.getRoot());
            this.markBinding = markBinding;
        }
    }

    public interface OnItemClick {
        void onCourseClicked(int position);
    }
}
