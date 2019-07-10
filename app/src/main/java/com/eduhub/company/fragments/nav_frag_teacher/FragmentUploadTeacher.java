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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduhub.company.R;
import com.eduhub.company.activities.ReportIssue;
import com.eduhub.company.activities.teacher.AssignmentT;
import com.eduhub.company.activities.teacher.AttendanceT;
import com.eduhub.company.activities.teacher.MarksT;
import com.eduhub.company.activities.teacher.ProjectsT;
import com.eduhub.company.activities.teacher.SyllabusT;
import com.eduhub.company.activities.teacher.TimeTableT;
import com.eduhub.company.activities.AddStudent;


public class FragmentUploadTeacher extends Fragment {

    CardView attendanceT,assignmentT,projectsT,marksT,timeTableT,syllabusT;
    RelativeLayout addStudent;

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
        addStudent = view.findViewById(R.id.addStudents);
        TextView report = view.findViewById(R.id.report);

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
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReportIssue.class));
            }
        });

        return view;
    }
}
