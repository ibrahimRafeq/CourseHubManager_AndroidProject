package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursehubmanager_androidproject.databinding.ItemDetailsBinding;

import java.util.List;

public class detailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ItemDetailsBinding binding;
    Context context;
    List<Course> courseList;
    OnItemClick onItemClick;

    public detailsAdapter(Context context, List<Course> courseList, OnItemClick onItemClick) {
        this.context = context;
        this.courseList = courseList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemDetailsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Course course = courseList.get(position);

        myViewHolder.binding.nameCTV.setText(course.getCourseName());
        myViewHolder.binding.priceCourse.setText(course.getCoursePrice());
        myViewHolder.binding.numberOfHours2.setText(String.valueOf(course.getCourseHours()));
        myViewHolder.binding.numberOfStudent.setText(String.valueOf(course.getCourse_NumberStudent()));
        myViewHolder.binding.lecturerCourse.setText(course.getCourseLecturer());
        myViewHolder.binding.description.setText(course.getCourseDetails());
        myViewHolder.binding.joinTheCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onMyCourseClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemDetailsBinding binding;
        public MyViewHolder(ItemDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClick{
        void onMyCourseClicked(int position);
    }
}
