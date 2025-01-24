package com.example.coursehubmanager_androidproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    long insertPerson(Person person);

    @Query("select * from Person")
    List<Person> getAllPerson();

    @Query("select * from Person where idPerson =:id")
    List<Person> getAllPersonById(long id);

    @Update
    int updatePerson(Person person);

    @Query("DELETE FROM Person WHERE idPerson = :userId")
    void deletePersonById(long userId);
}
