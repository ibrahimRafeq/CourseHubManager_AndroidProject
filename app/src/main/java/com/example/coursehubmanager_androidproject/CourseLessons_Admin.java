package com.example.coursehubmanager_androidproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager_androidproject.databinding.ActivityCourseLessonsAdminBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogAddLessonsBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogDeleteUserBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogEditLessonsBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseLessons_Admin extends AppCompatActivity {
    private ActivityCourseLessonsAdminBinding binding;
    private static final String ID_COURSE_LESSON = "idCourse";
    private List<Lessons> lessonsList;
    private DashboardAdapterLessons adapterLessons;
    private CourseDataBase courseDB;
    private AlertDialog dialog;
    private long idCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseLessonsAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idCourse = getIntent().getLongExtra(ID_COURSE_LESSON, -1);
        lessonsList = new ArrayList<>();
        courseDB = CourseDataBase.getDataBase(this);

        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        adapterLessons = new DashboardAdapterLessons(this, lessonsList, new DashboardAdapterLessons.OnItemClick() {
            @Override
            public void onUpdateClicked(int position) {
                long id = lessonsList.get(position).getIdLesson();
                showEditeLessonDialog(id);
            }

            @Override
            public void onDeleteClicked(int position) {
                long id = lessonsList.get(position).getIdLesson();
                String title = lessonsList.get(position).getTitle();
                String URL = lessonsList.get(position).getURL();
                deleteLesson(id, title, URL);
            }

        });
        binding.RVLessonsCourseAdmin.setAdapter(adapterLessons);
        binding.RVLessonsCourseAdmin.setLayoutManager(new LinearLayoutManager(this));

        binding.addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddLessonsDialog(idCourse);
            }
        });
    }

    public void showAddLessonsDialog(long courseId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogAddLessonsBinding bindingDialogLesson = DialogAddLessonsBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialogLesson.getRoot());

        bindingDialogLesson.addLessonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lessonTitle = bindingDialogLesson.etTitleLesson.getText().toString();
                String lessonURL = bindingDialogLesson.etURL.getText().toString();

                boolean isFound = false;
                for (int i = 0; i < lessonsList.size(); i++) {
                    if (lessonsList.get(i).getTitle().equals(lessonTitle) && lessonsList.get(i).getURL().equals(lessonURL)) {
                        isFound = true;
                        break;
                    }
                }
                if (lessonTitle.isEmpty() || lessonURL.isEmpty()) {
                    Toast.makeText(CourseLessons_Admin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (isFound) {
                        Toast.makeText(CourseLessons_Admin.this, "The lesson is already there", Toast.LENGTH_SHORT).show();
                    } else {
                        Lessons lessons = new Lessons(lessonTitle, lessonURL, courseId);
                        long id = courseDB.lessonsDao().insertLesson(lessons);
                        lessons.setIdLesson(id);
                        Toast.makeText(CourseLessons_Admin.this, "The addition was successfully completed", Toast.LENGTH_SHORT).show();
                        refreshLessonList();
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void showEditeLessonDialog(long lessonId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogEditLessonsBinding editLessonsBinding = DialogEditLessonsBinding.inflate(getLayoutInflater());
        builder.setView(editLessonsBinding.getRoot());

        editLessonsBinding.editLessonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lessonTitle = editLessonsBinding.etNewTitleLesson.getText().toString();
                String lessonURL = editLessonsBinding.etNewURL.getText().toString();
                boolean isFound = false;
                for (int i = 0; i < lessonsList.size(); i++) {
                    if (lessonsList.get(i).getTitle().equals(lessonTitle) && lessonsList.get(i).getURL().equals(lessonURL)) {
                        isFound = true;
                        break;
                    }
                }

                if (lessonTitle.isEmpty() || lessonURL.isEmpty()) {
                    Toast.makeText(CourseLessons_Admin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (isFound) {
                        Toast.makeText(CourseLessons_Admin.this, "The lesson is already there", Toast.LENGTH_SHORT).show();
                    } else {
                        Lessons lessons = new Lessons(lessonTitle, lessonURL, idCourse);
                        lessons.setIdLesson(lessonId);
                        long id = courseDB.lessonsDao().updateLesson(lessons);
                        Toast.makeText(CourseLessons_Admin.this, "The edition was successfully", Toast.LENGTH_SHORT).show();
                        refreshLessonList();
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }


    public void deleteLesson(long idLesson, String title, String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogDeleteUserBinding bindingDialog = DialogDeleteUserBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialog.getRoot());
        bindingDialog.deleteButDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lessons lessons = new Lessons(title, url, idCourse);
                lessons.setIdLesson(idLesson);
                courseDB.lessonsDao().deleteLesson(lessons);
                Toast.makeText(getApplicationContext(), "The delete was successfully", Toast.LENGTH_SHORT).show();
                refreshLessonList();
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();

    }

    private void refreshLessonList() {
        lessonsList.clear();
        lessonsList.addAll(courseDB.lessonsDao().getAllLesson(idCourse));
        adapterLessons.notifyDataSetChanged();
    }
}