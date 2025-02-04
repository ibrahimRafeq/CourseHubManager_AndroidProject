package com.example.coursehubmanager_androidproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    long insertCourse(Course course);

    @Query("select * from Course")
    List<Course> getAllCourse();

    @Query("select * from Course where category =:category")
    List<Course> getAllCourseCategory(String category);

    @Query("select * from Course where courseId =:id order by courseName asc")
    List<Course> getAllCourseDetails(long id);

    @Update
    int updateCourse(Course course);

    @Delete
    int deleteCourse(Course course);
}
