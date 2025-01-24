package com.example.coursehubmanager_androidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OnGoing extends Fragment {

    private CourseDataBase courseDB;
    private List<Course> courseList;
    private CourseAdapter courseAdapter;
    private static final String ARG_COURSE_ID = "course_Id";
    private static final String ARG_PERSON_ID = "Id_Person";
    private long courseId;
    private long personId;


    public OnGoing() {
    }

    public static OnGoing newInstance(long course_id, long person_id) {
        OnGoing fragment = new OnGoing();
        Bundle args = new Bundle();
        args.putLong(ARG_COURSE_ID, course_id);
        args.putLong(ARG_PERSON_ID, person_id);
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
        View view = inflater.inflate(R.layout.fragment_on_going, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.CourseListRVOnGoing);
        courseDB = CourseDataBase.getDataBase(getActivity());
        courseList = new ArrayList<>();

        courseList.addAll(courseDB.personCourseDao().getCoursesForPerson(personId));
        courseAdapter = new CourseAdapter(getActivity(), courseList, new CourseAdapter.OnItemClick() {
            @Override
            public void onCourseClicked(int position) {
                courseId = courseList.get(position).getCourseId();
                Intent intent = new Intent(getActivity(), Course_Content.class);
                intent.putExtra(ARG_COURSE_ID, courseId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}