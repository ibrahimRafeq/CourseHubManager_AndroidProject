package com.example.coursehubmanager_androidproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    private long courseId;
    @NonNull
    private String category;
    @NonNull
    private String courseName;
    @NonNull
    private String coursePrice;
    @NonNull
    private int courseHours;
    @NonNull
    @ColumnInfo(name = "nu_of_stu")
    private int course_NumberStudent;
    private String courseImage;
    private String courseLecturer;
    private String courseDetails;
    private boolean isJoined;
    private boolean isCompleted;
    private boolean isBookMark;



    public Course(@NonNull String category, @NonNull String courseName, @NonNull String coursePrice, int courseHours, int course_NumberStudent, String courseLecturer, String courseDetails) {
        this.category = category;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.courseHours = courseHours;
        this.course_NumberStudent = course_NumberStudent;
        this.courseLecturer = courseLecturer;
        this.courseDetails = courseDetails;
    }


    public boolean isBookMark() {
        return isBookMark;
    }

    public void setBookMark(boolean isBookMark) {
        this.isBookMark = isBookMark;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean isJoined) {
        this.isJoined = isJoined;
    }



    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @NonNull
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NonNull String courseName) {
        this.courseName = courseName;
    }

    @NonNull
    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(@NonNull String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public int getCourse_NumberStudent() {
        return course_NumberStudent;
    }

    public void setCourse_NumberStudent(int course_NumberStudent) {
        this.course_NumberStudent = course_NumberStudent;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseLecturer() {
        return courseLecturer;
    }

    public void setCourseLecturer(String courseLecturer) {
        this.courseLecturer = courseLecturer;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }
}
