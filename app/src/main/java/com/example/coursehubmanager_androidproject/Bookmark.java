package com.example.coursehubmanager_androidproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.coursehubmanager_androidproject.databinding.ActivityBookmarkBinding;
import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    ActivityBookmarkBinding binding;
    private List<Course> courseList;
    private CourseDataBase courseDB;
    private CourseAdapter courseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseList = new ArrayList<>();
        courseDB = CourseDataBase.getDataBase(this);
        courseList.addAll(courseDB.courseDao().getBookMarkCourses());
        courseAdapter = new CourseAdapter(this, courseList, new CourseAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {

            }
        });
        binding.bookMarkRV.setAdapter(courseAdapter);
        binding.bookMarkRV.setLayoutManager(new LinearLayoutManager(this));
    }
}