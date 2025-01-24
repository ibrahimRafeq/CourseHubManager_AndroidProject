package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.coursehubmanager_androidproject.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private long CorId;
    private long PerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        binding.bottomNav.setSelectedItemId(R.id.nav_home);
        addFragment(new HomeFragment());

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = getIntent();
                CorId = intent.getLongExtra("course_Id", -1);
                PerId = intent.getLongExtra("Id_Person", -1);

                if (item.getItemId() == R.id.nav_home) {
                    addFragment(new HomeFragment().newInstance(PerId));

                } else if (item.getItemId() == R.id.nav_myCourses) {
                    addFragment(new myCoursesFragment().newInstance(CorId, PerId));

                } else if (item.getItemId() == R.id.nav_profile) {
                    addFragment(new ProfileFragment().newInstance(PerId));
                }
                return true;
            }
        });

    }
    private void addFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
    }
}
