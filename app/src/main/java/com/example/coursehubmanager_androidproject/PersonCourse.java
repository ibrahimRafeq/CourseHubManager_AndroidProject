package com.example.coursehubmanager_androidproject;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Person.class, parentColumns = {"idPerson"},
        childColumns = {"personId"},
        onDelete = ForeignKey.CASCADE // إضافة سياسة الحذف
),
        @ForeignKey(entity = Course.class, parentColumns = {"courseId"},
                childColumns = {"courseId"},
                onDelete = ForeignKey.CASCADE // إضافة سياسة الحذف
        )
})
public class PersonCourse {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long personId;
    private long courseId;
    private boolean isCompleted;

    // Constructor
    public PersonCourse(long personId, long courseId) {
        this.personId = personId;
        this.courseId = courseId;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
