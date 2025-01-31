package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
    private static final String ARG_PARAM1 = "idPerson";
    List<Course> courseList;
    CourseDataBase courseDB;
    detailsAdapter detailsAdapter;
    private long idCourse;
    private long idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseDB = CourseDataBase.getDataBase(this);
        courseList = new ArrayList<>();
        idCourse = getIntent().getLongExtra("id", -1);
        idPerson = getIntent().getLongExtra(ARG_PARAM1, -1);
        courseList.addAll(courseDB.courseDao().getAllCourseDetails(idCourse));

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        long idPerson = sharedPreferences.getLong("id_person", -1);


        detailsAdapter = new detailsAdapter(this, courseList, new detailsAdapter.OnItemClick() {
            @Override
            public void onMyCourseClicked(int position) {

                long selectedCourseId = courseList.get(position).getCourseId();
                boolean isAlreadyRegistered = courseDB.personCourseDao().isPersonRegistered(idPerson, selectedCourseId);

                if (isAlreadyRegistered) {
                    Toast.makeText(Details.this, "أنت مسجل بالفعل في هذا الكورس", Toast.LENGTH_SHORT).show();
                } else {
                    PersonCourse personCourse = new PersonCourse(idPerson, selectedCourseId);
                    personCourse.setPersonId(idPerson);
                    personCourse.setCourseId(selectedCourseId);
                    Toast.makeText(Details.this, " " + idCourse + " " + idPerson, Toast.LENGTH_SHORT).show();
                    courseDB.personCourseDao().insertPersonCourse(personCourse);
                }
            }
        });
        binding.detailsRV.setAdapter(detailsAdapter);
        binding.detailsRV.setLayoutManager(new LinearLayoutManager(this));

    }
}