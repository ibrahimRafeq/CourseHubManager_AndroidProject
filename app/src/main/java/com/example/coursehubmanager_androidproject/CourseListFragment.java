package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    private String category;
    private CourseDataBase courseDB;
    private List<Course> courseList;
    private CourseAdapter courseAdapter;

    public CourseListFragment() {
    }

    public static CourseListFragment newInstance(String category) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
        courseDB = CourseDataBase.getDataBase(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.homeRV);
        courseList = new ArrayList<>();
        courseList.addAll(courseDB.courseDao().getAllCourseCategory(category));

        courseAdapter = new CourseAdapter(getContext(), courseList, new CourseAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                Intent intent = new Intent(getContext(), Details.class);
                intent.putExtra("id", courseList.get(position).getCourseId());
                startActivity(intent);
            }
        });
                recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
