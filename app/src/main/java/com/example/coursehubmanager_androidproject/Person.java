package com.example.coursehubmanager_androidproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    private long idPerson;
    @NonNull
    private String namePerson;
    @NonNull
    private String emailPerson;
    @NonNull
    private String passwordPerson;
    private String imagePerson;

    public Person(@NonNull String namePerson, @NonNull String emailPerson, @NonNull String passwordPerson) {
        this.namePerson = namePerson;
        this.emailPerson = emailPerson;
        this.passwordPerson = passwordPerson;
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    @NonNull
    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(@NonNull String namePerson) {
        this.namePerson = namePerson;
    }

    @NonNull
    public String getEmailPerson() {
        return emailPerson;
    }

    public void setEmailPerson(@NonNull String emailPerson) {
        this.emailPerson = emailPerson;
    }

    @NonNull
    public String getPasswordPerson() {
        return passwordPerson;
    }

    public void setPasswordPerson(@NonNull String passwordPerson) {
        this.passwordPerson = passwordPerson;
    }

    public String getImagePerson() {
        return imagePerson;
    }

    public void setImagePerson(String imagePerson) {
        this.imagePerson = imagePerson;
    }
}