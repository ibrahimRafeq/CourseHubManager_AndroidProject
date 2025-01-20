package com.example.coursehubmanager_androidproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.coursehubmanager_androidproject.databinding.ActivityDashboardBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogAddCourseBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogAddLessonsBinding;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private AlertDialog dialog;
    private CourseDataBase courseDB;
    private Notification notification;
    private List<Course> courseList;
    private DashboardAdapter dashboardAdapter;
    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean isGranted) {
            if (isGranted){
                notification.createNotification();
            }else {

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseDB = CourseDataBase.getDataBase(this);
        notification = new Notification(this);
        courseList = new ArrayList<>();
        courseList.addAll(courseDB.courseDao().getAllCourse());
        dashboardAdapter = new DashboardAdapter(getApplicationContext(), courseList, new DashboardAdapter.OnItemClick() {
            @Override
            public void onUpdateClicked(int position) {
                long id = courseList.get(position).getCourseId();
                showEditeCourseDialog(id);
            }
            @Override
            public void onDeleteClicked(int position) {
                long id = courseList.get(position).getCourseId();

            }
            @Override
            public void onAddLessonsClicked(int position) {
                long id = courseList.get(position).getCourseId();
                showAddLessonsDialog(id);
            }
            @Override
            public void onSelectedItem(int position){
                long id = courseList.get(position).getCourseId();
            }
        });
        binding.RVcourses.setAdapter(dashboardAdapter);
        binding.RVcourses.setLayoutManager(new LinearLayoutManager(this));

        binding.addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCourseDialog();
            }
        });
    }
    private void showAddCourseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogAddCourseBinding bindingDialog = DialogAddCourseBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialog.getRoot());
        bindingDialog.addCourseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName = bindingDialog.etNameCourse.getText().toString();
                String coursePrice = bindingDialog.etPrice.getText().toString();
                String courseNumHours = bindingDialog.numHoursCourse.getText().toString();
                String courseNumberStudent = bindingDialog.numberOfStudent.getText().toString();
                String courseLecturer = bindingDialog.nameLecturer.getText().toString();
                String courseDetails = bindingDialog.courseDecription.getText().toString();
                String selectedOp = "";
                int checkedId = bindingDialog.radioGroupDialog.getCheckedRadioButtonId();
                if (checkedId == -1) {
                    Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkedId == R.id.android) {
                        selectedOp = "Android";
                    } else if (checkedId == R.id.web) {
                        selectedOp = "Web";
                    } else if (checkedId == R.id.multiMidea) {
                        selectedOp = "Malte Media";
                    } else if (checkedId == R.id.cyberScurty) {
                        selectedOp = "Cyber Security";
                    } else if (checkedId == R.id.computerScience) {
                        selectedOp = "Computer Science";
                    }
                    Course course = new Course(selectedOp, courseName, coursePrice, Integer.valueOf(courseNumHours), Integer.valueOf(courseNumberStudent), courseLecturer, courseDetails);
                    long id = courseDB.courseDao().insertCourse(course);
                    course.setCourseId(id);
                    Toast.makeText(Dashboard.this, "The addition was successfully completed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                    {
                        notification.createNotification();
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
                        }
                    }
                }
            }
        });
        dialog = builder.create();
        dialog.show();

    }
    public void showAddLessonsDialog(long courseId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogAddLessonsBinding bindingDialogLesson = DialogAddLessonsBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialogLesson.getRoot());

        bindingDialogLesson.addLessonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lessonTitle = bindingDialogLesson.etTitleLesson.getText().toString();
                String lessonURL = bindingDialogLesson.etURL.getText().toString();

                    Lessons lessons = new Lessons(lessonTitle, lessonURL, courseId);
                    long id = courseDB.lessonsDao().insertLesson(lessons);
                    lessons.setIdLesson(id);
                    Toast.makeText(Dashboard.this, "The addition was successfully completed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                    {
                        notification.createNotification();
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
                        }
                    }
                }
        });

        dialog = builder.create();
        dialog.show();
    }
    public void showEditeCourseDialog(long courseId){

    }
    public void showEditeLessonDialog(long courseId){}
    public void showDeleteCourseDialog(long courseId, long lessonId){}
    public void showDeleteLessonDialog(long courseId, long lessonId){}
}
