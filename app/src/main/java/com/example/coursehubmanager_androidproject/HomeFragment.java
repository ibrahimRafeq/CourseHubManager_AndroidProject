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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
                        tab.setIcon(R.drawable.baseline_filter_1_24);
                        tab.getOrCreateBadge().setText("4");
                        break;

                    case 1:
                        tab.setText("Web");
                        tab.setIcon(R.drawable.baseline_filter_2_24);
                        break;

                    case 2:
                        tab.setText("Malte Media");
                        tab.setIcon(R.drawable.baseline_filter_3_24);
                        break;
                    case 3:
                        tab.setText("Cyber Security");
                        tab.setIcon(R.drawable.baseline_filter_3_24);
                        break;
                    case 4:
                        tab.setText("Computer Science");
                        tab.setIcon(R.drawable.baseline_filter_3_24);
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
            return CourseListFragment.newInstance(category);
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}
