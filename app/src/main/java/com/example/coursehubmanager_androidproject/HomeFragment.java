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

public class HomeFragment extends Fragment {
    private final int count = 5;
    private static final String ARG_PARAM1 = "idPerson";
    private long IdPerson;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(long idPerson) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, idPerson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            IdPerson = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);
        TabLayout tap = view.findViewById(R.id.tap);

        viewPager2.setAdapter(new ViewPagerAdapter(HomeFragment.this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tap, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Android");
                        tab.setIcon(R.drawable.android);
                        break;
                    case 1:
                        tab.setText("Web");
                        tab.setIcon(R.drawable.web);
                        break;
                    case 2:
                        tab.setText("Malte Media");
                        tab.setIcon(R.drawable.multimedia);
                        break;
                    case 3:
                        tab.setText("Cyber Security");
                        tab.setIcon(R.drawable.siper);
                        break;
                    case 4:
                        tab.setText("Computer Science");
                        tab.setIcon(R.drawable.computer);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        return view;
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull HomeFragment fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            String category = "";
            switch (position) {
                case 0:
                    category = "Android";
                    break;
                case 1:
                    category = "Web";
                    break;
                case 2:
                    category = "Malte Media";
                    break;
                case 3:
                    category = "Cyber Security";
                    break;
                case 4:
                    category = "Computer Science";
                    break;
            }
            return CourseListFragment.newInstance(category, IdPerson);
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}
