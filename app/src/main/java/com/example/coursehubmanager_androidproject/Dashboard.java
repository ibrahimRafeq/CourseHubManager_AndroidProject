package com.example.coursehubmanager_androidproject;

import android.Manifest;
import android.content.Intent;
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
import com.example.coursehubmanager_androidproject.databinding.DialogDeleteUserBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogEditeCourseBinding;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private AlertDialog dialog;
    private CourseDataBase courseDB;
    private Notification notification;
    private List<Course> courseList;
    private DashboardAdapter dashboardAdapter;
    private static final String ID_COURSE_LESSON = "idCourse";
    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean isGranted) {
            if (isGranted) {
                notification.createNotification();
            } else {

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
                String category = courseList.get(position).getCategory();
                String courseName = courseList.get(position).getCourseName();
                String coursePrice = courseList.get(position).getCoursePrice();
                int courseNumHours = courseList.get(position).getCourseHours();
                int courseNumberStudent = courseList.get(position).getCourse_NumberStudent();
                String courseLecturer = courseList.get(position).getCourseLecturer();
                String courseDetails = courseList.get(position).getCourseDetails();
                showDeleteCourseDialog(id, category, courseName, coursePrice, courseNumHours, courseNumberStudent, courseLecturer, courseDetails);
            }

            @Override
            public void onSelectedItem(int position) {
                long id = courseList.get(position).getCourseId();
                Intent intent = new Intent(getApplicationContext(), CourseLessons_Admin.class);
                intent.putExtra(ID_COURSE_LESSON, id);
                startActivity(intent);

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

    private void showAddCourseDialog() {
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
                    refreshCourseList();
                    dialog.dismiss();

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        notification.createNotification();
                    } else {
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

    public void showEditeCourseDialog(long courseId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogEditeCourseBinding dialogEditeCourse = DialogEditeCourseBinding.inflate(getLayoutInflater());
        builder.setView(dialogEditeCourse.getRoot());

        dialogEditeCourse.editeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNewName = dialogEditeCourse.etNameNewCourse.getText().toString();
                String courseNewPrice = dialogEditeCourse.etNewPrice.getText().toString();
                String courseNewNumHours = dialogEditeCourse.NewNumHoursCourse.getText().toString();
                String courseNewNumberStudent = dialogEditeCourse.newNumberOfStudent.getText().toString();
                String courseNewLecturer = dialogEditeCourse.newNameLecturer.getText().toString();
                String courseNewDetails = dialogEditeCourse.newCourseDescription.getText().toString();
                String newSelectedOp = "";
                int checkedId = dialogEditeCourse.radioGroupDialog.getCheckedRadioButtonId();
                if (checkedId == -1) {
                    Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkedId == R.id.android) {
                        newSelectedOp = "Android";
                    } else if (checkedId == R.id.web) {
                        newSelectedOp = "Web";
                    } else if (checkedId == R.id.multiMidea) {
                        newSelectedOp = "Malte Media";
                    } else if (checkedId == R.id.cyberScurty) {
                        newSelectedOp = "Cyber Security";
                    } else if (checkedId == R.id.computerScience) {
                        newSelectedOp = "Computer Science";
                    }
                    Course course = new Course(newSelectedOp, courseNewName, courseNewPrice, Integer.valueOf(courseNewNumHours), Integer.valueOf(courseNewNumberStudent), courseNewLecturer, courseNewDetails);
                    course.setCourseId(courseId);
                    long id = courseDB.courseDao().updateCourse(course);
                    Toast.makeText(Dashboard.this, "The Edition was successfully completed", Toast.LENGTH_SHORT).show();
                    refreshCourseList();
                    dialog.dismiss();
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        notification.createNotification();
                    } else {
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

    public void showDeleteCourseDialog(long courseId, String category, String courseName, String coursePrice, int courseNumHours, int courseNumberStudent, String courseLecturer, String courseDetails) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogDeleteUserBinding deleteBinding = DialogDeleteUserBinding.inflate(getLayoutInflater());
        builder.setView(deleteBinding.getRoot());

        deleteBinding.deleteButDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course = new Course(category, courseName, coursePrice, courseNumHours, courseNumberStudent, courseLecturer, courseDetails);
                course.setCourseId(courseId);
                long id = courseDB.courseDao().deleteCourse(course);
                Toast.makeText(Dashboard.this, "The Delete Was Successfully", Toast.LENGTH_SHORT).show();
                refreshCourseList();
                dialog.dismiss();
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notification.createNotification();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
                    }
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void refreshCourseList() {
        courseList.clear();
        courseList.addAll(courseDB.courseDao().getAllCourse());
        dashboardAdapter.notifyDataSetChanged();
    }
}
