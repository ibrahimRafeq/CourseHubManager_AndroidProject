package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursehubmanager_androidproject.databinding.ItemCourseDashboardBinding;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Course> courseList;
    ItemCourseDashboardBinding dashboardBinding;
    OnItemClick itemClick;
    public DashboardAdapter(Context context, List<Course> courseList, OnItemClick itemClick) {
        this.context = context;
        this.courseList = courseList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dashboardBinding = ItemCourseDashboardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(dashboardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Course course = courseList.get(position);
        myViewHolder.dashboardBinding.nameTheCourse.setText(course.getCourseName());
        myViewHolder.dashboardBinding.deleteCourseDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onDeleteClicked(position);
            }
        });
        myViewHolder.dashboardBinding.editCourseDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onUpdateClicked(position);
            }
        });
        myViewHolder.dashboardBinding.addLessonsDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onAddLessonsClicked(position);
            }
        });
        myViewHolder.dashboardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onSelectedItem(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseDashboardBinding dashboardBinding;
        public MyViewHolder(ItemCourseDashboardBinding dashboardBinding) {
            super(dashboardBinding.getRoot());
            this.dashboardBinding = dashboardBinding;
        }
    }

    public interface OnItemClick{
        void onUpdateClicked(int position);
        void onDeleteClicked(int position);
        void onAddLessonsClicked(int position);
        void onSelectedItem(int position);

    }
}
