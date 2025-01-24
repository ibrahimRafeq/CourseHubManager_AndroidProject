package com.example.coursehubmanager_androidproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonCourseDao {
    @Insert
    void insertPersonCourse(PersonCourse personCourse);

    @Query("SELECT c.* FROM Course c " +
            "INNER JOIN PersonCourse pc ON c.courseId = pc.courseId " +
            "WHERE pc.personId = :personId AND c.isCompleted = 0")
    List<Course> getCoursesForPerson(long personId);

    @Query("SELECT c.* FROM Course c " +
            "INNER JOIN PersonCourse pc ON c.courseId = pc.courseId " +
            "WHERE pc.personId = :personId AND pc.isCompleted = 1")
    List<Course> getCompletedCoursesForPerson(long personId);

    @Query("UPDATE PersonCourse SET isCompleted = 1 " +
            "WHERE personId = :personId AND courseId = :courseId AND isCompleted = 0")
    void markCourseAsCompleted(long personId, long courseId);

    @Query("SELECT * FROM PersonCourse WHERE courseId = :courseId")
    List<PersonCourse> getPeopleForCourse(long courseId);

    @Query("DELETE FROM PersonCourse WHERE personId = :personId AND courseId = :courseId")
    void deletePersonCourse(long personId, long courseId);
}
