package com.example.coursehubmanager_androidproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityBookmarkBinding;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    private ActivityBookmarkBinding binding;
    private List<Course> courseList;
    private CourseDataBase courseDB;
    private CourseAdapter courseAdapter;
    private long idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        idPerson = sharedPreferences.getLong("id_person", -1);
        courseList = new ArrayList<>();
        courseDB = CourseDataBase.getDataBase(this);
        courseList.addAll(courseDB.personCourseDao().getBookMarkCourses(idPerson));
        courseAdapter = new CourseAdapter(this, courseList, new CourseAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {

            }
        });
        binding.bookMarkRV.setAdapter(courseAdapter);
        binding.bookMarkRV.setLayoutManager(new LinearLayoutManager(this));
    }
}