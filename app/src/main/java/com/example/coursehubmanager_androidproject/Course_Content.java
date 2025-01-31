package com.example.coursehubmanager_androidproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityCourseContentBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogAddCourseBinding;

import java.util.ArrayList;
import java.util.List;

public class Course_Content extends AppCompatActivity {
    private ActivityCourseContentBinding binding;
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

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        lessonsAdapter = new LessonsAdapter(this, lessonsList, new LessonsAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                try {
                    String url = lessonsList.get(position).getURL();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Course_Content.this, "The link could not be opened.!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.RVLessonsCourse.setAdapter(lessonsAdapter);
        binding.RVLessonsCourse.setLayoutManager(new LinearLayoutManager(this));


        binding.bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTheBookMark();
            }
        });

        binding.addCompleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTheCompleteCourse();
            }
        });

        if (lessonsList.isEmpty()) {
            Toast.makeText(this, "No lessons found for this course", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTheBookMark(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Course_Content.this);
        builder.setTitle("BookMark ♦");
        builder.setMessage("This course will be added to the bookmark list");

        builder.setPositiveButton("OK", (dialog, which) -> {
            courseDB.personCourseDao().updateBookMark(idPerson, idCourse);
            Toast.makeText(Course_Content.this, "This course has been added to the bookMark", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            Toast.makeText(Course_Content.this, "Canceled", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addTheCompleteCourse(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Course_Content.this);
        builder.setTitle("Complete Course ✔✔");
        builder.setMessage("The page will be UPDATE !");
        builder.setPositiveButton("OK", (dialog, which) -> {

        courseDB.personCourseDao().markCourseAsCompleted(idPerson, idCourse);
        Toast.makeText(Course_Content.this, "This course has been added to the complete course", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    });
        builder.setNegativeButton("NO", (dialog, which) -> {
            Toast.makeText(Course_Content.this, "Canceled", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}