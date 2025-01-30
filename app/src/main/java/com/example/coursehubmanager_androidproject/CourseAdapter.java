package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager_androidproject.databinding.ItemCourseBinding;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Course> courseList;
    ItemCourseBinding courseBinding;
    OnItemClick itemClick;

    public CourseAdapter(Context context, List<Course> courseList, OnItemClick itemClick) {
        this.context = context;
        this.courseList = courseList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        courseBinding = ItemCourseBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(courseBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Course course = courseList.get(position);
        myViewHolder.courseBinding.nameCourseTV.setText(course.getCourseName());
        myViewHolder.courseBinding.instructorName.setText(course.getCourseLecturer());
        myViewHolder.courseBinding.description.setText(course.getCourseDetails());
        myViewHolder.courseBinding.getRoot().setOnClickListener(new View.OnClickListener() {
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
        ItemCourseBinding courseBinding;

        public MyViewHolder(ItemCourseBinding courseBinding) {
            super(courseBinding.getRoot());
            this.courseBinding = courseBinding;
        }
    }

    public interface OnItemClick {
        void onCourseClicked(int position);
    }
}
