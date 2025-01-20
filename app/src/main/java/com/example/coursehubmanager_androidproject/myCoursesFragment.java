package com.example.coursehubmanager_androidproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class myCoursesFragment extends Fragment {
    private static final String ARG_COURSE_ID = "course_Id";
    private static final String ARG_PERSON_ID = "Id_Person";
    private long courseId;
    private long personId;
    private final int count = 2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public myCoursesFragment() {
    }

    public static myCoursesFragment newInstance(long courseId, long personId) {
        myCoursesFragment fragment = new myCoursesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_COURSE_ID, courseId);
        args.putLong(ARG_PERSON_ID, personId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getLong(ARG_COURSE_ID);
            personId = getArguments().getLong(ARG_PERSON_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_courses, container, false);
        ViewPager2 viewPager2MyCourse = view.findViewById(R.id.viewPagerMyCourse);
        TabLayout tapMyCourse = view.findViewById(R.id.tapMyCourse);

        viewPager2MyCourse.setAdapter(new ViewPagerAdapter(myCoursesFragment.this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tapMyCourse, viewPager2MyCourse, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("On Going");
                        tab.setIcon(R.drawable.baseline_filter_1_24);
                        tab.getOrCreateBadge().setText("4");
                        break;

                    case 1:
                        tab.setText("Completed");
                        tab.setIcon(R.drawable.baseline_filter_2_24);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        return view;
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull myCoursesFragment fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            String category = "";
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new OnGoing().newInstance(courseId, personId);
                    category = "On Going";
                    break;
                case 1:
                    fragment = new Completed();
                    category = "Completed";
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}