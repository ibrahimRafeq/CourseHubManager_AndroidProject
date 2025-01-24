package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursehubmanager_androidproject.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private CourseDataBase db;
    private List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = CourseDataBase.getDataBase(this);
        personList = new ArrayList<>();
        personList.addAll(db.personDao().getAllPerson());

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameP = binding.editNameSignUp.getText().toString();
                String emailP = binding.editEmailSignUp.getText().toString();
                String passwordP = binding.editPasswordSignUp.getText().toString();
                Person person = new Person(nameP, emailP, passwordP);
                boolean found = false;

                if (nameP.isEmpty() || emailP.isEmpty() || passwordP.isEmpty()) {
                    Toast.makeText(Register.this, "Make sure all data is entered", Toast.LENGTH_SHORT).show();
                } else {
                    int i = 0;
                    for (i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getEmailPerson().equals(emailP) && personList.get(i).getPasswordPerson().equals(passwordP)) {
                            found = true;
                        }
                    }
                    if (found) {
                        Toast.makeText(Register.this, "This account is already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        long id = db.personDao().insertPerson(person);
                        person.setIdPerson(id);
                        Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        intent.putExtra("idPerson", person.getIdPerson());
                        startActivity(intent);
                    }
                }

            }
        });

        binding.tvLogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}