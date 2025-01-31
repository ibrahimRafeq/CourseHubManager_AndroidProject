
package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityBookMarkCourseContentBinding;

import java.util.ArrayList;
import java.util.List;

public class BookMark_CourseContent extends AppCompatActivity {
    private ActivityBookMarkCourseContentBinding binding;
    private List<Lessons> lessonsList;
    private LessonsAdapter lessonsAdapter;
    private CourseDataBase courseDB;
    private static final String ARG_COURSE_ID = "course_Id";
    private long idCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookMarkCourseContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseDB = CourseDataBase.getDataBase(this);
        lessonsList = new ArrayList<>();
        idCourse = getIntent().getLongExtra(ARG_COURSE_ID, -1);

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        lessonsAdapter = new LessonsAdapter(this, lessonsList, new LessonsAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                Toast.makeText(BookMark_CourseContent.this, "ok", Toast.LENGTH_SHORT).show();
                String url = "https://example.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        binding.RVLessonsCourseBookMark.setAdapter(lessonsAdapter);
        binding.RVLessonsCourseBookMark.setLayoutManager(new LinearLayoutManager(this));
    }
}