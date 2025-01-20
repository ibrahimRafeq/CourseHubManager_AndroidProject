package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.coursehubmanager_androidproject.databinding.ActivityDetailsBinding;
import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
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
        courseList.addAll(courseDB.courseDao().getAllCourseDetails(idCourse));
        detailsAdapter = new detailsAdapter(this, courseList, new detailsAdapter.OnItemClick() {
            @Override
            public void onMyCourseClicked(int position) {

                long selectedCourseId = courseList.get(position).getCourseId();
                courseDB.courseDao().joinCourse(selectedCourseId);
                Intent intent = new Intent(Details.this, HomeActivity.class);
                intent.putExtra("course_Id", idCourse);
                Toast.makeText(Details.this, "The course has been successfully registered", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        binding.detailsRV.setAdapter(detailsAdapter);
        binding.detailsRV.setLayoutManager(new LinearLayoutManager(this));

    }
}