package com.eduhub.company.fragments.nav_frag_teacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduhub.company.R;
import com.eduhub.company.activities.Assignment;
import com.eduhub.company.activities.AssignmentT;
import com.eduhub.company.activities.Attendance;
import com.eduhub.company.activities.AttendanceT;
import com.eduhub.company.activities.Marks;
import com.eduhub.company.activities.MarksT;
import com.eduhub.company.activities.Projects;
import com.eduhub.company.activities.ProjectsT;
import com.eduhub.company.activities.Syllabus;
import com.eduhub.company.activities.SyllabusT;
import com.eduhub.company.activities.TimeTable;
import com.eduhub.company.activities.TimeTableT;


public class FragmentUploadTeacher extends Fragment {

    CardView attendanceT,assignmentT,projectsT,marksT,timeTableT,syllabusT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_upload_teacher,null,false);

        attendanceT = view.findViewById(R.id.attendanceT);
        assignmentT  = view.findViewById(R.id.assignmentT);
        projectsT = view.findViewById(R.id.projectsT);
        marksT = view.findViewById(R.id.marksT);
        timeTableT = view.findViewById(R.id.timeTableT);
        syllabusT = view.findViewById(R.id.syllabusT);

        attendanceT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendanceT.class);
                startActivity(intent);
            }
        });
        assignmentT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssignmentT.class);
                startActivity(intent);
            }
        });
        marksT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MarksT.class);
                startActivity(intent);
            }
        });
        projectsT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectsT.class);
                startActivity(intent);
            }
        });
        timeTableT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTableT.class);
                startActivity(intent);
            }
        });
        syllabusT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SyllabusT.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
