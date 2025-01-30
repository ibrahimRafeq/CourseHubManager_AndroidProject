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
            "WHERE pc.personId = :personId AND pc.isCompleted = 0")
    List<Course> getCoursesForPerson(long personId);

    @Query("SELECT c.* FROM Course c " +
            "INNER JOIN PersonCourse pc ON c.courseId = pc.courseId " +
            "WHERE pc.personId = :personId AND pc.isCompleted = 1")
    List<Course> getCompletedCoursesForPerson(long personId);

    @Query("UPDATE PersonCourse SET isCompleted = 1 " +
            "WHERE personId = :personId AND courseId = :courseId AND isCompleted = 0")
    void markCourseAsCompleted(long personId, long courseId);

    @Query("UPDATE PersonCourse SET isBookMark = 1 " +
            "WHERE personId = :personId AND courseId = :courseId AND isBookMark = 0")
    void updateBookMark(long personId, long courseId);

    @Query("UPDATE PersonCourse SET isBookMark = 0 " +
            "WHERE personId = :personId AND courseId = :courseId AND isBookMark = 1")
    void updateBookMark_2(long personId, long courseId);
    @Query("SELECT c.* FROM Course c " +
            "INNER JOIN PersonCourse pc ON c.courseId = pc.courseId " +
            "WHERE pc.personId = :personId AND pc.isBookMark = 1")
    List<Course> getBookMarkCourses(long personId);

    @Query("SELECT * FROM PersonCourse WHERE courseId = :courseId")
    List<PersonCourse> getPeopleForCourse(long courseId);

    @Query("DELETE FROM PersonCourse WHERE personId = :personId AND courseId = :courseId")
    void deletePersonCourse(long personId, long courseId);
}
