package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityCourseContentBinding;

import java.util.ArrayList;
import java.util.List;

public class Course_Content extends AppCompatActivity {
    ActivityCourseContentBinding binding;
    private List<Lessons> lessonsList;
    private LessonsAdapter lessonsAdapter;
    private CourseDataBase courseDB;
    private static final String ARG_COURSE_ID = "course_Id";
    private long idCourse;
    private long idPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseDB = CourseDataBase.getDataBase(this);
        lessonsList = new ArrayList<>();
        Intent intent = getIntent();
        idCourse = intent.getLongExtra(ARG_COURSE_ID, -1);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        idPerson = sharedPreferences.getLong("id_person", -1);

//        if (lessonsList.isEmpty()) {
//            Toast.makeText(this, "No lessons found for this course", Toast.LENGTH_SHORT).show();
//        } else {
//
//        }

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        lessonsAdapter = new LessonsAdapter(this, lessonsList, new LessonsAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                Toast.makeText(Course_Content.this, "ok", Toast.LENGTH_SHORT).show();

                String url = "https://example.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });
        binding.RVLessonsCourse.setAdapter(lessonsAdapter);
        binding.RVLessonsCourse.setLayoutManager(new LinearLayoutManager(this));

        binding.doYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.checkBoxFinished.isChecked()) {
                    courseDB.personCourseDao().markCourseAsCompleted(idPerson, idCourse);
                    Toast.makeText(Course_Content.this, "This course has been added to the complete course", Toast.LENGTH_SHORT).show();
                }

                if (binding.bookMark.isChecked()) {
                    courseDB.personCourseDao().updateBookMark(idPerson, idCourse);
                    Toast.makeText(Course_Content.this, "This course has been added to the bookMark", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        binding.back1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Course_Content.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}