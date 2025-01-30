package com.example.coursehubmanager_androidproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityBookmarkBinding;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    private ActivityBookmarkBinding binding;
    private List<Course> courseList;
    private CourseDataBase courseDB;
    private MarkBookAdapter courseAdapter;
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
        courseAdapter = new MarkBookAdapter(this, courseList, new MarkBookAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                courseDB.personCourseDao().updateBookMark_2(idPerson, courseList.get(position).getCourseId());
                Toast.makeText(Bookmark.this, "heee", Toast.LENGTH_SHORT).show();
            }
        });
        binding.bookMarkRV.setAdapter(courseAdapter);
        binding.bookMarkRV.setLayoutManager(new LinearLayoutManager(this));
    }
}