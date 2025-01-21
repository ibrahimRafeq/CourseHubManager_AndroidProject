package com.example.coursehubmanager_androidproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Person.class, Lessons.class}, version = 26, exportSchema = false)
public abstract class CourseDataBase extends RoomDatabase {
    public abstract CourseDao courseDao();
    public abstract LessonsDao lessonsDao();
    public abstract PersonDao personDao();

    private static volatile CourseDataBase INSTANCE;

    static CourseDataBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CourseDataBase.class, "course_database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
