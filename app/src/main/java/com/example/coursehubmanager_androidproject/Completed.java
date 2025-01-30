package com.example.coursehubmanager_androidproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Completed extends Fragment {

    CourseAdapter courseAdapter;
    CourseDataBase courseDB;
    List<Course> courseList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Completed() {
    }

    public static Completed newInstance(String param1, String param2) {
        Completed fragment = new Completed();
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
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.CourseListRVCompleted);
        courseDB = CourseDataBase.getDataBase(getActivity());
        courseList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        long idPerson = sharedPreferences.getLong("id_person", -1);

        courseList.addAll(courseDB.personCourseDao().getCompletedCoursesForPerson(idPerson));
        courseAdapter = new CourseAdapter(getActivity(), courseList, new CourseAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                long courseId_id = courseList.get(position).getCourseId();
                Intent intent = new Intent(getActivity(), Course_Content.class);
                intent.putExtra("course_Id", courseId_id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}