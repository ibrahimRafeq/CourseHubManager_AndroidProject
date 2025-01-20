package com.example.coursehubmanager_androidproject;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseDB = CourseDataBase.getDataBase(this);
        lessonsList = new ArrayList<>();
        Intent intent = getIntent();
        idCourse =  intent.getLongExtra(ARG_COURSE_ID, -1);


//        if (lessonsList.isEmpty()) {
//            Toast.makeText(this, "No lessons found for this course", Toast.LENGTH_SHORT).show();
//        } else {
//
//        }

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        lessonsAdapter = new LessonsAdapter(this, lessonsList, new LessonsAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {

            }
        });
        binding.RVLessonsCourse.setAdapter(lessonsAdapter);
        binding.RVLessonsCourse.setLayoutManager(new LinearLayoutManager(this));

        binding.doYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.checkBoxFinished.isChecked()){
                    courseDB.courseDao().updateCourseCompletion(idCourse);
                    Toast.makeText(Course_Content.this, "This course has been added to the complete course" , Toast.LENGTH_SHORT).show();
                }

                if (binding.bookMark.isChecked()){
                    courseDB.courseDao().updateBookMark(idCourse);
                    Toast.makeText(Course_Content.this, "This course has been added to the bookMark" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Course_Content.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
    }
}