package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
            public void onDelete(int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Bookmark.this);
                builder.setTitle("BookMark ♦");
                builder.setMessage("This Course will be removed from the list");
                builder.setPositiveButton("OK", (dialog, which) -> {

                    courseDB.personCourseDao().updateBookMark_2(idPerson, courseList.get(position).getCourseId());
                    Toast.makeText(Bookmark.this, "Removed from list", Toast.LENGTH_SHORT).show();
                    refreshPersonList();
                });

                builder.setNegativeButton("NO", (dialog, which) -> {
                    Toast.makeText(Bookmark.this, "Canceled", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onCourseClicked(int position) {
                Intent intent = new Intent(Bookmark.this, BookMark_CourseContent.class);
                intent.putExtra("course_Id", courseList.get(position).getCourseId());
                startActivity(intent);
            }
        });

        binding.bookMarkRV.setAdapter(courseAdapter);
        binding.bookMarkRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void refreshPersonList() {
        courseList.clear();
        courseList.addAll(courseDB.personCourseDao().getBookMarkCourses(idPerson));
        courseAdapter.notifyDataSetChanged();
    }
}