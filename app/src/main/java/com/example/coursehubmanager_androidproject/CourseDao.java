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

    @Query("SELECT * FROM Course WHERE isJoined = 1 and isCompleted = 0")
    List<Course> getJoinedCourses();

    @Query("UPDATE Course SET isJoined = 1 WHERE courseId =:courseId")
    void joinCourse(long courseId);

    @Query("SELECT * FROM Course WHERE isCompleted = 1")
    List<Course> getCompletedCourses();

    @Query("UPDATE Course SET isCompleted = 1 WHERE courseId =:courseId")
    void updateCourseCompletion(long courseId);

    @Query("SELECT * FROM Course WHERE isBookMark = 1")
    List<Course> getBookMarkCourses();

    @Query("UPDATE Course SET isBookMark = 1 WHERE courseId =:courseId")
    void updateBookMark(long courseId);

    @Update
    int updateCourse(Course course);

    @Delete
    int deleteCourse(Course course);
}
