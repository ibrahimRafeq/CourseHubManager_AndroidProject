package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursehubmanager_androidproject.databinding.ItemLessonDashboardBinding;
import java.util.List;

public class DashboardAdapterLessons extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Lessons> lessonsList;
    ItemLessonDashboardBinding lessonDashboardBinding;
    private OnItemClick itemClick;
    public DashboardAdapterLessons(Context context, List<Lessons> lessonsList, OnItemClick itemClick) {
        this.context = context;
        this.lessonsList = lessonsList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        lessonDashboardBinding = ItemLessonDashboardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(lessonDashboardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Lessons lessons = lessonsList.get(position);
        myViewHolder.lessonDashboardBinding.nameTheLesson.setText(lessons.getTitle());
        myViewHolder.lessonDashboardBinding.deleteLessonDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onDeleteClicked(position);
            }
        });
        myViewHolder.lessonDashboardBinding.editLessonDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onUpdateClicked(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemLessonDashboardBinding lessonDashboardBinding;
        public MyViewHolder(ItemLessonDashboardBinding lessonDashboardBinding) {
            super(lessonDashboardBinding.getRoot());
            this.lessonDashboardBinding = lessonDashboardBinding;
        }
    }

    public interface OnItemClick{
        void onUpdateClicked(int position);
        void onDeleteClicked(int position);
    }
}
