package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coursehubmanager_androidproject.databinding.ActivityLoginBinding;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    private CourseDataBase personDB;
    private List<Person> personList;
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "rememberMe";
    private static final String KEY_ID_PERSON = "id_person"; // مفتاح لتخزين Id_Person

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        personDB = CourseDataBase.getDataBase(this);
        personList = new ArrayList<>();
        personList = personDB.personDao().getAllPerson();

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
            binding.editEmailLognUp.setText(savedEmail);
            binding.editPasswordLognIn.setText(savedPassword);
        }

        binding.btnLognIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailP = binding.editEmailLognUp.getText().toString();
                String passwordP = binding.editPasswordLognIn.getText().toString();
                boolean found = false;
                if (emailP.isEmpty() || passwordP.isEmpty()) {
                    Toast.makeText(Login.this, "Make sure all data is entered", Toast.LENGTH_SHORT).show();
                } else {
                    int index = 0;
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getEmailPerson().equals(emailP) && personList.get(i).getPasswordPerson().equals(passwordP)) {
                            found = true;
                            index = i;
                            break;
                        }
                    }
                    if (found) {
                        handleRememberMe(emailP, passwordP);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong(KEY_ID_PERSON, personList.get(index).getIdPerson());
                        editor.apply();
                        Intent intent = new Intent(Login.this, HomeActivity.class);
                        intent.putExtra("Id_Person", personList.get(index).getIdPerson());
                        startActivity(intent);
                    } else if (emailP.equals(ADMIN_EMAIL) && passwordP.equals(ADMIN_PASSWORD)) {
                        handleRememberMe(emailP, passwordP);
                        Intent intent = new Intent(Login.this, Dashboard.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Hello Mr.Admin", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "The input does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        binding.signInTextVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }

    private void handleRememberMe(String email, String password) {
        if (binding.checkBoxRememberMe.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.putBoolean(KEY_REMEMBER_ME, true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}