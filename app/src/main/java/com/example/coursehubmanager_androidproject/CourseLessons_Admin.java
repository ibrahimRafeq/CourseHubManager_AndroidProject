package com.example.coursehubmanager_androidproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.coursehubmanager_androidproject.databinding.ActivityCourseLessonsAdminBinding;
import java.util.ArrayList;
import java.util.List;

public class CourseLessons_Admin extends AppCompatActivity {
    private ActivityCourseLessonsAdminBinding binding;
    private static final String ID_COURSE_LESSON = "idCourse";
    private List<Lessons> lessonsList;
    private DashboardAdapterLessons adapterLessons;
    private CourseDataBase courseDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseLessonsAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        long idCourse = getIntent().getLongExtra(ID_COURSE_LESSON, -1);
        lessonsList = new ArrayList<>();
        courseDB = CourseDataBase.getDataBase(this);

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        adapterLessons = new DashboardAdapterLessons(this, lessonsList, new DashboardAdapterLessons.OnItemClick() {
            @Override
            public void onUpdateClicked(int position) {

            }

            @Override
            public void onDeleteClicked(int position) {

            }

            @Override
            public void onAddLessonsClicked(int position) {

            }
        });
        binding.RVLessonsCourseAdmin.setAdapter(adapterLessons);
        binding.RVLessonsCourseAdmin.setLayoutManager(new LinearLayoutManager(this));

    }
}