package com.example.coursehubmanager_androidproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = {"courseId"},
        childColumns = {"course_Id"},
        onDelete = ForeignKey.CASCADE // إضافة سياسة الحذف
))
public class Lessons {
    @PrimaryKey(autoGenerate = true)
    private long idLesson;
    @NonNull
    private String title;
    @NonNull
    private String URL;
    private String image;
    @ColumnInfo(name = "course_Id")
    private long idCourseLessons;


    public Lessons(@NonNull String title, @NonNull String URL, long idCourseLessons) {
        this.title = title;
        this.URL = URL;
        this.idCourseLessons = idCourseLessons;
    }

    public long getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(long idLesson) {
        this.idLesson = idLesson;
    }

    public long getIdCourseLessons() {
        return idCourseLessons;
    }

    public void setIdCourseLessons(long idCourseLessons) {
        this.idCourseLessons = idCourseLessons;
    }

    @NonNull
    public String getURL() {
        return URL;
    }

    public void setURL(@NonNull String URL) {
        this.URL = URL;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
