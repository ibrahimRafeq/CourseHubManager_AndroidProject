package com.example.coursehubmanager_androidproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface LessonsDao {
    @Insert
    long insertLesson(Lessons lessons);
    @Update
    int updateLesson(Lessons lessons);
    @Query("select * from Lessons where course_Id =:id")
    List<Lessons> getAllLesson(long id);
    @Query("select * from Lessons where course_Id =:id")
    List<Lessons> searchLesson(long id);
    @Delete
    int deleteLesson(Lessons lessons);
}
